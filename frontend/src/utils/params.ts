import { WordsPagFetchParams } from "../types/global"

export const getParams = (searchParams : URLSearchParams) : WordsPagFetchParams => {
    return {
        title: searchParams.get('title') ?? '',
        lang: searchParams.get('lang') ?? '',
        size: Number(searchParams.get('size')) || 10,
        page : Number(searchParams.get('page'))
    }
}