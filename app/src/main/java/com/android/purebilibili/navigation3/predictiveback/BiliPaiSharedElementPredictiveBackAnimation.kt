package com.android.purebilibili.navigation3.predictiveback

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.defaultTransitionSpec

import androidx.navigationevent.NavigationEventTransitionState
import com.android.purebilibili.navigation3.BiliPaiNavKey

internal class BiliPaiSharedElementPredictiveBackAnimation(
    private val exitDirection: BiliPaiPredictiveBackExitDirection = BiliPaiPredictiveBackExitDirection.ALWAYS_RIGHT,
) : BiliPaiPredictiveBackAnimationHandler {
    override suspend fun onBackPressed(
        transitionState: NavigationEventTransitionState?,
        currentPageKey: BiliPaiNavKey?,
    ) = Unit

    @Composable
    override fun Modifier.predictiveBackAnimationDecorator(
        transitionState: NavigationEventTransitionState?,
        contentPageKey: Any,
        currentPageKey: BiliPaiNavKey?,
    ): Modifier = this

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onPredictivePopTransitionSpec(
        swipeEdge: Int,
    ): ContentTransform {
        val slideOut = when (exitDirection) {
            BiliPaiPredictiveBackExitDirection.FOLLOW_GESTURE -> {
                if (swipeEdge == 0) slideOutHorizontally(targetOffsetX = { it })
                else slideOutHorizontally(targetOffsetX = { -it })
            }
            BiliPaiPredictiveBackExitDirection.ALWAYS_RIGHT -> slideOutHorizontally(targetOffsetX = { it })
            BiliPaiPredictiveBackExitDirection.ALWAYS_LEFT -> slideOutHorizontally(targetOffsetX = { -it })
        }
        val slideIn = when (exitDirection) {
            BiliPaiPredictiveBackExitDirection.FOLLOW_GESTURE -> {
                if (swipeEdge == 0) slideInHorizontally(initialOffsetX = { -it / 4 })
                else slideInHorizontally(initialOffsetX = { it / 4 })
            }
            BiliPaiPredictiveBackExitDirection.ALWAYS_RIGHT -> slideInHorizontally(initialOffsetX = { -it / 4 })
            BiliPaiPredictiveBackExitDirection.ALWAYS_LEFT -> slideInHorizontally(initialOffsetX = { it / 4 })
        }
        return ContentTransform(
            targetContentEnter = slideIn,
            initialContentExit = slideOut,
            sizeTransform = null,
        )
    }

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onPopTransitionSpec(): ContentTransform =
        ContentTransform(
            targetContentEnter = EnterTransition.None,
            initialContentExit = ExitTransition.None,
            sizeTransform = null,
        )

    override fun AnimatedContentTransitionScope<Scene<BiliPaiNavKey>>.onTransitionSpec(): ContentTransform =
        defaultTransitionSpec<BiliPaiNavKey>().invoke(this)
}
