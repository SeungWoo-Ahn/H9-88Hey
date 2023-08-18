package com.softeer.mycarchiving.ui.archiving

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softeer.mycarchiving.navigation.ArchivingDestinations
import com.softeer.mycarchiving.navigation.MainDestination
import com.softeer.mycarchiving.ui.HyundaiAppState
import com.softeer.mycarchiving.ui.archiving.archivingdetail.archivingDetailScreen
import com.softeer.mycarchiving.ui.archiving.archivingmain.archiveMainScreen
import com.softeer.mycarchiving.ui.component.ArchiveBottomBar
import com.softeer.mycarchiving.ui.component.ArchiveNavigateBar

fun NavController.navigateToArchive(navOptions: NavOptions? = null) {
    navigate(
        MainDestination.ARCHIVING.route,
        navOptions
    )
}

fun NavGraphBuilder.makingArchiveGraph(
    appState: HyundaiAppState,
) {
    composable(
        route = MainDestination.ARCHIVING.route
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                ArchiveNavigateBar(
                    onStartAreaClick = appState.navController::popBackStack,
                    onEndAreaClick = { appState.navigateToMainDestination(MainDestination.MY_ARCHIVING) }
                )
            },
            bottomBar = {
                if (appState.currentArchivingDestinations?.needBottomBar == true) {
                    ArchiveBottomBar()
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                NavHost(
                    navController = appState.archivingNavController,
                    startDestination = ArchivingDestinations.ARCHIVING_MAIN.route
                ) {
                    archiveMainScreen(
                        moveDetailPage = { appState.navigateToArchivingDestination(ArchivingDestinations.ARCHIVING_DETAIL) },
                        onBackClick = appState.navController::popBackStack
                    )
                    archivingDetailScreen(
                        onBackClick = appState.archivingNavController::popBackStack
                    )
                }
            }
        }
    }
}

