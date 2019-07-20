package com.tsurkis.recipesearch.injection

import android.content.Context
import com.tsurkis.recipesearch.app.ThreadManager
import com.tsurkis.recipesearch.app.ViewModelFactory
import com.tsurkis.recipesearch.data.local.api.AppDatabase
import com.tsurkis.recipesearch.data.local.api.RecipeDAO
import com.tsurkis.recipesearch.data.local.api.RecipeDAOManager
import com.tsurkis.recipesearch.data.remote.api.RecipeSearchAPI
import com.tsurkis.recipesearch.data.repository.RecipeRepository
import com.tsurkis.recipesearch.data.repository.model.RecipeModelConverter
import retrofit2.Retrofit
import java.lang.RuntimeException
import java.util.concurrent.Executor

class Injector private constructor(
    private val applicationContextModule: ApplicationContextModule,
    private val localAPIModule: LocalAPIModule,
    private val remoteAPIModule: RemoteAPIModule,
    private val repositoryModule: RepositoryModule,
    private val threadModule: ThreadModule,
    private val utilsModule: UtilsModule,
    private val viewModelFactoryModule: ViewModelFactoryModule
) {

    private val utilsProvider: UtilsProvider = UtilsProvider(utilsModule = utilsModule)

    private val threadManagerProvider: ThreadManagerProvider = ThreadManagerProvider(threadModule = ThreadModule())

    private val remoteAPIProvider: RemoteAPIProvider =
        RemoteAPIProvider(
            remoteAPIModule = remoteAPIModule,
            networkThread = threadModule.provideNetworkThread(),
            converter = utilsProvider.converter
        )

    private val localApiProvider: LocalAPIProvider =
        LocalAPIProvider(
            localAPIModule = localAPIModule,
            applicationContext = applicationContextModule.provideApplicationContext(),
            converter = utilsProvider.converter
        )

    private val repositoryProvider: RepositoryProvider =
        RepositoryProvider(
            repositoryModule = repositoryModule,
            recipeSearchAPI = remoteAPIProvider.recipeSearchAPI,
            recipeDAOManager = localApiProvider.recipeDaoManager
        )

    private val viewModelFactoryProvider: ViewModelFactoryProvider =
        ViewModelFactoryProvider(
            viewModelFactoryModule = viewModelFactoryModule,
            ioThread = threadManagerProvider.threadManager.ioThread,
            recipeRepository = repositoryProvider.recipeRepository
        )

    companion object {

        private var instance: Injector? = null

        fun provider(): Injector {
            instance ?: throw RuntimeException("Injector.instantiate method not called. Please initialize your Injector")
            return instance!!
        }

        fun instantiate(applicationContext: Context) {
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

    fun provideViewModelFactory(): ViewModelFactory = viewModelFactoryProvider.viewModelFactory
}

class ThreadManagerProvider(threadModule: ThreadModule) {
    val threadManager: ThreadManager =
        threadModule.provideThreadManager(
            networkThread = threadModule.provideNetworkThread(),
            ioThread = threadModule.provideIOThread()
        )
}

class UtilsProvider(utilsModule: UtilsModule) {
    val converter: RecipeModelConverter = utilsModule.provideRecipeModuleConverter()
}

class RepositoryProvider(
    repositoryModule: RepositoryModule,
    recipeSearchAPI: RecipeSearchAPI,
    recipeDAOManager: RecipeDAOManager
) {
    val recipeRepository: RecipeRepository =
        repositoryModule.provideRecipeRepository(
            recipeRemoteAPI = recipeSearchAPI,
            recipeDAOManager = recipeDAOManager
        )
}

class ViewModelFactoryProvider(
    viewModelFactoryModule: ViewModelFactoryModule,
    ioThread: Executor,
    recipeRepository: RecipeRepository
) {
    val viewModelFactory: ViewModelFactory =
        viewModelFactoryModule.provideViewModelFactory(
            backThread = ioThread,
            recipeRepository = recipeRepository
        )
}

class RemoteAPIProvider(
    remoteAPIModule: RemoteAPIModule,
    networkThread: Executor,
    converter: RecipeModelConverter
) {
    val retrofitClient: Retrofit =
        remoteAPIModule.provideRetrofit(
            okHttpClient = remoteAPIModule.provideOKHttpClient(
                loggingInterceptor = remoteAPIModule.provideInterceptor()
            ),
            converterFactory = remoteAPIModule.provideGsonConverterFactory(),
            remoteAPIBackThread = networkThread
        )

    val recipeSearchAPI: RecipeSearchAPI =
        remoteAPIModule.provideRecipeSearchAPI(
            api = remoteAPIModule.provideTheMealDBAPIAPI(
                retrofitClient = retrofitClient
            ),
            converter = converter
        )
}

class LocalAPIProvider(
    localAPIModule: LocalAPIModule,
    applicationContext: Context,
    converter: RecipeModelConverter
) {
    val database: AppDatabase =
        localAPIModule.provideAppDatabase(
            applicationContext = applicationContext
        )

    val recipeDao: RecipeDAO =
        localAPIModule.provideRecipeDao(database)

    val recipeDaoManager: RecipeDAOManager =
        localAPIModule.provideRecipeDAOManager(
            recipeDao = recipeDao,
            converter = converter
        )
}