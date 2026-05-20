package com.android.purebilibili.navigation3

import com.android.purebilibili.navigation.ScreenRoutes
import kotlin.test.Test
import kotlin.test.assertEquals

class BiliPaiNavBackStackPolicyTest {

    @Test
    fun initialBackStack_usesOnboardingWhenRequired() {
        assertEquals(
            listOf(BiliPaiNavKey.Unknown("onboarding")),
            resolveInitialBiliPaiBackStack(
                firstRoute = ScreenRoutes.Home.route,
                onboardingRequired = true
            )
        )
    }

    @Test
    fun push_skipsDuplicateTopEntry() {
        val stack = listOf(BiliPaiNavKey.Home)

        assertEquals(stack, pushBiliPaiNavKey(stack, BiliPaiNavKey.Home))
    }

    @Test
    fun pop_keepsRootEntry() {
        assertEquals(
            listOf(BiliPaiNavKey.Home),
            popBiliPaiNavKey(listOf(BiliPaiNavKey.Home))
        )
        assertEquals(
            listOf(BiliPaiNavKey.Home),
            popBiliPaiNavKey(listOf(BiliPaiNavKey.Home, BiliPaiNavKey.VideoDetail("BV1")))
        )
    }
}
