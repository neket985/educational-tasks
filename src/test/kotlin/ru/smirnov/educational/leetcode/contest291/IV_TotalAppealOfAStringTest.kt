package ru.smirnov.educational.leetcode.contest291

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class IV_TotalAppealOfAStringTest {

    @Test
    fun substringsTest() {
        assertEquals(listOf("1234", "123", "234", "12", "23", "34", "1", "2", "3", "4"), IV_TotalAppealOfAString.substrings("1234"))
    }

    @Test
    fun countDistinct() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1), DataAmount.ofMega(1000),
            {
                assertEquals(
                    28,
                    IV_TotalAppealOfAString.appealsCount("abbca")
                )
            },
            {
                assertEquals(
                    20,
                    IV_TotalAppealOfAString.appealsCount("code")
                )
            },
            {
                assertEquals(
                    10871925,
                    IV_TotalAppealOfAString.appealsCount("jffqgvjzoulfnzaniqpjlriqdogyplgteifddtnhyxbzsbyllsnhopualudvdohnypwqapuujyccjktycjccbuiglvruccoyiawjcdjlumthwyirlrxumhkfikmqrjalidgfgfmhhwxrrepqnsjlqreanvnjjdocjlajajboiyplbsvnpvwvehihenpgkypovxjoouwrqkyutkfyralbamjhjsidljspemqiytgiofaeyngztqmdpredrulhepqwrryugsvrqnuywsokyykrjdpgkftgfyroooemyvqbpfaepyhsbilmepjczjczzddasqrvlkdagotixfunwogdlhftcnnrozdjgrezgcipmuzkhinzzvtqfrpksicvlgbiozeyvgsapaxqljyyocwmoystvigczzpecyjoqqejqfnvggstaqyjtsjuvezqmzthcfynvtxwrzegkssnvnibaojbllexjzxzmepfgqpgvjgkrruwengonivmgwrauilfxdjsnywuaycnwowvykdelufbkhuxclepfelnpmkhaesvbmfrsmfbogrqyxpagglxmippfabeyilkrijzxgusblkjjjgnpzdmaoulprqleeturrpolxuxkuaxfnrhubbsqpmeecwofxfnbhhxvzoftnqsfgtlivcmufmwmwqltmhvuggflllnoniamidilprrqqprnsbjfnvdktcsulipffcxamjxtlsecoyraqexzstmdgeaytxgvzfmwcvztabkejssrqivkimgyphcaloejqfiqymfwwrhguuqgxdmqvrydjzzjwqocirukytgvgdshlcqmjtzfrektmviwgeejtvrddocgwdfliuxnhgulqyphuijxqdppoacpwxojsfbdksordrcvcgvzebnrskhjrcmyahhruopmedynccwhdh")
                )
            },
        )
    }
}