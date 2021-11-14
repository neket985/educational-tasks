package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import ru.smirnov.educational.ya2021.DMatrixRotation.matrixGaussian
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.random.Random

internal class DMatrixRotationTest {

    @Test
    fun process() {

        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(30),
            DataAmount.ofMega(256),
            listOf(
                {
                    assert(
                        listOf(
                            listOf(-1.0, 0.0),
                            listOf(1.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(2, listOf(listOf(0.0, 1.0))), 6
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(0.0, -1.0, 0.0, 0.0),
                            listOf(0.0, 1.0, 0.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(
                                4, listOf(
                                    listOf(0.0, 0.0, 0.0, 1.0),
                                    listOf(1.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 1.0, 0.0)
                                )
                            ),
                            6
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(-0.666666666667, 0.333333333333, 0.666666666667),
                            listOf(0.666666666667, -0.333333333333, -0.666666666667)
                        ).roundContains(
                            DMatrixRotation.process(
                                3,
                                listOf(
                                    listOf(0.6666666666666, 0.6666666666666, 0.3333333333333),
                                    listOf(-0.3333333333333, 0.6666666666666, -0.6666666666666)
                                )
                            ),
                            6
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(-0.22919, -0.41411, 0.01141, -0.30659, 0.82575),
                            listOf(0.22919, 0.41411, -0.01141, 0.30659, -0.82575)
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(-0.24045, -0.17761, 0.01603, -0.83299, -0.46531),
                                    listOf(-0.94274, 0.12031, 0.00566, 0.29741, -0.09098),
                                    listOf(-0.02069, 0.30417, -0.93612, -0.13759, 0.10865),
                                    listOf(0.02155, -0.83065, -0.35109, 0.32365, -0.28556),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(0.07863783, 0.7048799, 0.08914089, -0.64230492, -0.27651168),
                            listOf(-0.07863783, -0.7048799, -0.08914089, 0.64230492, 0.27651168),
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(-0.63470728, 0.41984536, 0.41569193, 0.25708079, 0.42659843),
                                    listOf(-0.36418389, 0.06244462, -0.82734663, -0.24066123, 0.3479231),
                                    listOf(0.67691426, 0.33798442, -0.05984083, 0.17555011, 0.62702062),
                                    listOf(-0.01095148, -0.45688226, 0.36217501, -0.65773717, 0.47681205)
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    val angle = 3.1415926 / 15
                    assert(
                        listOf(
                            listOf(0.0, Math.sin(angle), 0.0, 0.0, Math.cos(angle)),
                            listOf(0.0, -Math.sin(angle), 0.0, 0.0, -Math.cos(angle)),
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(1.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 1.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 1.0, 0.0),
                                    listOf(0.0, Math.cos(angle), 0.0, 0.0, -Math.sin(angle)),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    val angle = 3.1415926 / 12
                    assert(
                        listOf(
                            listOf(0.0, 0.0, 0.0, 1.0, 0.0),
                            listOf(0.0, 0.0, 0.0, -1.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(1.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, Math.sin(angle), Math.cos(angle), 0.0, 0.0),
                                    listOf(0.0, Math.cos(angle), -Math.sin(angle), 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 1.0),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    assert(
                        """
                            -0.10055483532123037 -0.2792648657992645 -0.11937521604157313 -0.07023713233581212 0.1588367091749166 -0.19093231568255487 -0.17057811329942976 0.06522576853569718 -0.36815783700891125 0.26798707547945144 -0.29043394010993123 0.001950016583127323 0.6321865490984628 -0.10077718643569203 -0.18678831354298672 -0.24751147435605797
                        """.trimIndent().parseMatrix().run { this.plusElement(this[0].map { -it }) }
                            .roundContains(
                                DMatrixRotation.process(
                                    16,
                                    """
                                        -0.028392943185519007 0.3121058190441873 0.17547564383400804 -0.06190979569821279 0.06087706258577623 0.16331031836971469 -0.0658436462709455 0.18883085485556134 0.09479724498472943 -0.2013789094758504 -0.20928722377724673 -0.3773355148150074 0.4827856314492917 0.15657784443153921 0.17279899550868788 0.523076690375168
                                        -0.4429483590548033 -0.18460171428629274 0.4949556077318036 0.0216973830366921 0.21337992968629835 -0.0939256522894528 -0.08290685080879687 -0.3084893949906063 0.009540346843173092 0.3882314871859014 0.1555683891200062 -0.32299278481415405 -0.13440168098504676 -0.12474174703778373 0.2138654145102991 0.09576822817939043
                                        -0.05509546893268137 0.09873793394207389 0.3163137080449338 0.13817082383326268 -0.14880241236350744 -0.06265662142854754 -0.17929340506029498 -0.10231807961463281 0.45543130540622484 -0.13567973264278496 0.03771321164478823 -0.1618799722059494 0.18622748540012124 0.3615793245429401 -0.3671207724664406 -0.4957162749397309
                                        -0.09229306996010596 0.21333535622569527 -0.011961537568025038 0.001388002220220133 0.08248069808762565 -0.31762481505161333 -0.11896812552754675 0.5529866769920542 0.019068189947357816 0.3798268831106303 -0.27641982914507807 -0.09351563174962131 -0.40544203344555163 0.21281565111317108 -0.25911221914806387 0.10766012860777625
                                        0.5334612472672763 -0.0757026600146499 0.46683011337467606 -0.09128848936397412 -0.24202157974290123 0.06062277357453316 0.29038048424734253 0.3162195136376066 -0.2482096317063049 0.22925082628806942 0.18579732355334222 -0.17731106338187602 0.064949447576598 0.012516210860754778 0.1297009053018265 -0.18851487299220104
                                        0.13764187864551092 0.23501870882287224 -0.1209866419704006 -0.1863829357862386 0.05132753915702744 0.5706358842277345 -0.11374621148941216 -0.3649555377753428 -0.14648578044245797 0.4568143354805308 -0.12583303533380286 0.026768405812577355 -0.06546862306863566 0.37885193302470366 -0.07887029670473311 -0.03647748434932086
                                        -0.0024784972683042916 0.2758630214261798 -0.166935969116739 0.01833152002074937 -0.2043760205425505 -0.0025901080042133734 -0.03410977076284071 -0.1513707047357481 -0.2113115264864589 -0.16897150213367812 -0.3574222582641244 -0.5742211555315189 -0.22156440122222515 -0.3431392671366373 0.11304513846149519 -0.34574249827289516
                                        0.4257107655538814 -0.1887989250500962 0.157929835962368 0.3351293862023555 -0.22227613229616203 -0.07262783825518197 -0.20025662847948236 -0.29910681600925115 0.0982088524599825 0.11118181324093107 -0.28175109017004585 0.008475408570159236 -0.0436076916990745 -0.26274720131925705 -0.35138498023110315 0.4071261159993793
                                        -0.04565642235562059 0.13702062954620878 0.41536319323181725 0.34809073872956714 0.434837274154322 0.18777263135696037 0.2635007147123887 0.011853272268603311 -0.20261439828195663 -0.2229513613260874 -0.41239075382220824 0.3134871220085811 -0.09455350670795154 -0.019626413467645522 -0.021314364683016812 -0.15050539328384682
                                        0.08670884367782083 0.5218120062823327 -0.1479106414947655 0.426403319795045 -0.016266857802808357 -0.43487209249648945 0.2317632195617956 -0.24466306910767008 -0.05695890179337949 0.2741924270498874 0.18143011475409285 0.09092462449358092 0.22848844202043966 0.07890810890429639 0.18832170788140673 0.00594994411738569
                                        0.10826845335520784 -0.04851127809341402 0.1032988858377719 -0.5458277093917346 0.05868256857575595 -0.4569291205156551 0.34759699224196433 -0.37487025041489114 -0.07965430033927838 -0.19238480194146104 -0.2213681839846077 -0.01774790581945999 -0.07650813089364418 0.28158775663424684 -0.11418928796858752 0.11352038658710471
                                        0.038718042056373456 0.5057140575079951 0.28362813238494744 -0.4192880340566857 0.03399468324315065 -0.07044107755619602 -0.3574991421918096 -0.010533095378741558 -0.043152089297668034 -0.01882485641969619 0.170357664384315 0.3133966156319217 0.044432148344757096 -0.4451707969943027 -0.14785939671682882 -0.031675851655070524
                                        -0.33751752639366206 -0.003187408882511799 0.14401604077446312 -0.0811548245300524 -0.6512052767747581 0.02346100347493078 0.09429142860226514 0.020141281721121944 0.12399450979559698 0.15785475737363647 -0.3935652839085773 0.3688664581149491 0.0641474439857606 0.0040782667035033455 0.2987769888825295 -0.013478980530044381
                                        0.39017846420149366 -0.08900155178253827 -0.06079640216968124 -0.1153138328946764 0.33682479149577566 -0.11778974409864236 -0.20238473320473013 0.006718781778510666 0.4957521262552656 0.13166050166711618 -0.2726804457168128 0.0678350893476752 -0.0036459096046477513 -0.059369634830757814 0.5189835500832365 -0.1989298026272808
                                        0.08707706859594631 -0.07387900998019799 0.12018839037235335 0.13910220259630596 -0.1128577264970971 -0.19004193128091995 -0.5943423589275422 -0.04476898212552565 -0.44338327199756 -0.25994727360438985 0.04549089406165327 0.10316969995027217 -0.13240186420685485 0.3945235844610607 0.31443501846128213 0.01190327947576829
                                """.trimIndent().parseMatrix()
                                ),
                                6
                            )
                    )
                },
                *(0..10).map {
                    {
                        val n = Random.nextInt(2, 17)
                        println("matrix ${n}x$n")
                        val randomOrthogonalMatrix = generateOrthogonalMatrix(n, n.toDouble().pow(3).toInt()).shuffled()
                        assert(
                            listOf(randomOrthogonalMatrix.first()).run { this.plusElement(this[0].map { -it }) }
                                .roundContains(
                                    DMatrixRotation.process(
                                        n,
                                        randomOrthogonalMatrix.subList(1, n)
                                    ),
                                    6
                                )
                        )
                    }
                }.toTypedArray()
            )
        )
    }

    @Test
    fun gaussianMatrixTest() {
        val matrix =
            listOf(
                listOf(0.0, 0.0, 1.0, 1.0).mapIndexed { i, it -> DeterminantContainer(i, it) },
                listOf(1.0, 2.0, 3.0, 2.0).mapIndexed { i, it -> DeterminantContainer(i, it) },
                listOf(4.0, 1.0, 0.0, 3.0).mapIndexed { i, it -> DeterminantContainer(i, it) }
            )

        val result = matrixGaussian(4, matrix)
        assertEquals(0.0, result[1][0].value.absoluteValue)
        assertEquals(0.0, result[2][0].value.absoluteValue)
        assertEquals(0.0, result[2][1].value.absoluteValue)
    }

    private fun generateOrthogonalMatrix(n: Int, rotationsNum: Int): List<List<Double>> {
        val eMatrix = (0 until n).map { row ->
            (0 until n).map { col ->
                if (row == col) 1.0 else 0.0
            }
        }
        var resultMatrix = eMatrix

        repeat(rotationsNum) {
            val (r1, r2) = (0 until n).shuffled()
            val angle = Random.nextDouble(Math.PI / 16, Math.PI / 4 - Math.PI / 16) +
                    Random.nextInt(4) * Math.PI / 2

            val rotateMatrix = eMatrix.map { it.toMutableList() }
            rotateMatrix[r1][r1] = Math.cos(angle)
            rotateMatrix[r1][r2] = -Math.sin(angle)

            rotateMatrix[r2][r2] = Math.cos(angle)
            rotateMatrix[r2][r1] = Math.sin(angle)

            resultMatrix = (resultMatrix * rotateMatrix)
        }

        val determinant = resultMatrix.determinant()
        assert(roundEquals(determinant, 1.0, 3))

        println(resultMatrix.beautifyString())
        return resultMatrix
    }

    private operator fun List<List<Double>>.times(other: List<List<Double>>): List<List<Double>> {
        val size = this.size
        return (0 until size).map { rowIndex ->
            (0 until size).map { colIndex ->
                (0 until size).map { multiplyIndex ->
                    this[rowIndex][multiplyIndex] * other[multiplyIndex][colIndex]
                }.sumByDouble { it }
            }
        }
    }

    private fun List<List<Double>>.determinant(): Double {
        val n = this.size
        val gauss = matrixGaussian(n, this.map { it.mapIndexed { i, it -> DeterminantContainer(i, it) } })
        return (0 until n).map { i ->
            gauss[i][i].value
        }.reduce { acc, d ->
            acc * d
        }
    }

    private fun List<List<Double>>.beautifyString() = this.joinToString("\n") { it.joinToString(" ") }
    private fun String.parseMatrix(): List<List<Double>> = this.split("\n").map { it.split(" ").map { it.toDouble() } }

    //один из списков ответов содержит ответ, совподающий с полученным с точностью до {{sign}} знаков
    private fun List<List<Double>>.roundContains(item: List<Double>, sign: Int) =
        any { list ->
            if (list.size != item.size) return@any false
            list.forEachIndexed { index, d ->
                if (!roundEquals(d, item[index], sign)) return@any false
            }
            return@any true
        }

    private fun roundEquals(first: Double, second: Double, sign: Int) =
        ((first - second) * 10.0.pow(sign)).toInt() == 0

}