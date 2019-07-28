package com.tsurkis.recipesearch.injection

import android.app.Activity
import android.content.Context
import com.tsurkis.recipesearch.app.ThreadManager
import com.tsurkis.recipesearch.app.ViewModelFactory
import com.tsurkis.recipesearch.custom.wrappers.ImageLoader
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import com.tsurkis.recipesearch.ui.screens.recipe.search.RecipeSearchActivity

class Injector private constructor(
    private val applicationContextModule: ApplicationContextModule,
    private val localAPIModule: LocalAPIModule,
    private val remoteAPIModule: RemoteAPIModule,
    private val repositoryModule: RepositoryModule,
    private val threadModule: ThreadModule,
    private val utilsModule: UtilsModule,
    private val viewModelFactoryModule: ViewModelFactoryModule
) {

    companion object {

        private var instance: Injector? = null

        fun inject(): Injector {
            return instance
                ?: throw RuntimeException("Injector.instantiate method not called. Please initialize your Injector class")
        }

        fun instantiate(applicationContext: Context) {
            if (instance != null) throw RuntimeException("Injector already initialized! Please do not use the instantiate method")
            instance =
                Injector(
                    applicationContextModule = ApplicationContextModule(applicationContext = applicationContext),
                    localAPIModule = LocalAPIModule(),
                    remoteAPIModule = RemoteAPIModule(),
                    repositoryModule = RepositoryModule(),
                    threadModule = ThreadModule(),
                    utilsModule = UtilsModule(),
                    viewModelFactoryModule = ViewModelFactoryModule()
                )
        }
    }

    private val viewModelFactory: ViewModelFactory

    private val imageLoader: ImageLoader

    init {
        val threadManager: ThreadManager =
            threadModule.provideThreadManager(
                networkThread = threadModule.provideNetworkThread(),
                ioThread = threadModule.provideIOThread()
            )

        val recipeModelConverter: RecipeModelConverter = utilsModule.provideRecipeModuleConverter()

        val recipeSearchAPI: RecipeSearchAPI = remoteAPIModule.provideRecipeSearchAPI(
            api = remoteAPIModule.provideTheMealDBAPI(
                retrofitClient = remoteAPIModule.provideRetrofit(
                    okHttpClient = remoteAPIModule.provideOKHttpClient(
                        loggingInterceptor = remoteAPIModule.provideInterceptor()
                    ),
                    converterFactory = remoteAPIModule.provideGsonConverterFactory(),
                    remoteAPIBackThread = threadManager.networkThread
                )
            ),
            converter = recipeModelConverter
        )

        val recipeDAOManager = localAPIModule.provideRecipeDAOManager(
            recipeDao = localAPIModule.provideRecipeDao(
                appDatabase = localAPIModule.provideAppDatabase(
                    applicationContext = applicationContextModule.provideApplicationContext()
                )
            ),
            converter = recipeModelConverter
        )

        val recipeRepository: RecipeRepository = repositoryModule.provideRecipeRepository(
            recipeRemoteAPI = recipeSearchAPI,
            recipeDAOManager = recipeDAOManager
        )

        viewModelFactory = viewModelFactoryModule.provideViewModelFactory(
            backThread = threadManager.ioThread,
            recipeRepository = recipeRepository
        )

        imageLoader = utilsModule.provideImageLoaderWrapper(
            imageLoaderInstance = utilsModule.provideImageLoaderInstance()
        )
    }

    fun into(activity: Activity) {
        when (activity) {
            is RecipeSearchActivity -> {
                activity.viewModelFactory = viewModelFactory
                activity.imageLoader = imageLoader
            }
        }
    }
}