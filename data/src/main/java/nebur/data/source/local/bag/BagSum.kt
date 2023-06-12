package nebur.data.source.local.bag

import androidx.room.ColumnInfo

object BagSumArg {
    const val SUM = "sum"
}

data class BagSum(
    @ColumnInfo(name = BagSumArg.SUM) val sum: Int,
)