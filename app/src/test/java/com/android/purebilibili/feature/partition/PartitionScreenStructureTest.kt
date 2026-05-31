package com.android.purebilibili.feature.partition

import java.io.File
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PartitionScreenStructureTest {

    @Test
    fun `partition page uses side rail and feed list layout`() {
        val source = loadSource("app/src/main/java/com/android/purebilibili/feature/partition/PartitionScreen.kt")

        assertTrue(source.contains("PartitionSideRail("))
        assertTrue(source.contains("PartitionVideoList("))
        assertTrue(source.contains("PartitionVideoRow("))
        assertTrue(source.contains("SettingsManager.getHomeSettings(context)"))
        assertTrue(source.contains("resolveEffectiveLiquidGlassEnabled("))
        assertTrue(source.contains("BottomBarLiquidIndicatorSurface("))
        assertTrue(source.contains("liquidGlassIndicatorEnabled = liquidGlassIndicatorEnabled"))
        assertTrue(source.contains("partitionSideRailSweepSelection("))
        assertTrue(source.contains("CardPositionManager.recordVideoCardPosition("))
        assertTrue(source.contains("videoCoverSharedElementKey("))
        assertTrue(source.contains("LocalVideoCardSharedElementSourceRoute.current"))
        assertTrue(source.contains("VideoRepository.getPopularVideos(page = currentPage)"))
        assertTrue(source.contains("VideoRepository.getRegionVideos(tid = partition.id, page = currentPage)"))
        assertFalse(source.contains("LazyVerticalGrid("))
    }

    @Test
    fun `side rail sweep resolves visible item under finger`() {
        val visibleItems = listOf(
            PartitionSideRailVisibleItem(index = 0, offset = 8, size = 48),
            PartitionSideRailVisibleItem(index = 1, offset = 60, size = 48),
            PartitionSideRailVisibleItem(index = 2, offset = 112, size = 48)
        )

        assertTrue(
            resolvePartitionSideRailSweepIndex(
                pointerY = 64f,
                visibleItems = visibleItems,
                itemCount = 3
            ) == 1
        )
        assertTrue(
            resolvePartitionSideRailSweepIndex(
                pointerY = 180f,
                visibleItems = visibleItems,
                itemCount = 3
            ) == null
        )
        assertTrue(
            resolvePartitionSideRailSweepIndex(
                pointerY = 64f,
                visibleItems = visibleItems,
                itemCount = 1
            ) == null
        )
    }

    private fun loadSource(path: String): String {
        val normalizedPath = path.removePrefix("app/")
        val sourceFile = listOf(
            File(path),
            File(normalizedPath)
        ).firstOrNull { it.exists() }
        require(sourceFile != null) { "Cannot locate $path from ${File(".").absolutePath}" }
        return sourceFile.readText()
    }
}
