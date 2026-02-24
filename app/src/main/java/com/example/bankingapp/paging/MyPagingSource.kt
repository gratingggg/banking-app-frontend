package com.example.bankingapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bankingapp.models.PagedResponse
import okio.IOException
import retrofit2.HttpException

class MyPagingSource<T: Any>(
    private val fetchData: suspend (page: Int) -> PagedResponse<T>
): PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(anchorPosition = it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val currentPage = params.key ?: 0
            val response: PagedResponse<T> = fetchData(currentPage)

            return LoadResult.Page(
                data = response.content,
                prevKey = null,
                nextKey = if(currentPage >= response.page.totalPages - 1) null else currentPage + 1
            )
        } catch (e: IOException){
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
}