import { useEffect, useState } from "react";
import { FetchError, Language, Word, WordsPagFetchParams } from "../types/global";
import Search from "../components/search/Search";
import Fallback from "../components/words/Fallback";
import { fetchLanguages, fetchWords, find } from "../api/fetchers";
import WordContainer from "../components/words/WordsContainer";
import { buildFallbackMessage } from "../utils/error";
import { createSearchParams, useNavigate, useParams, useSearchParams } from "react-router-dom";

const defaultParams : WordsPagFetchParams = { page : 0 , size : 10, lang: '', title: ''};

export default function WordsPage() {
    const [words, setWords]           = useState<Word[]>([]);
    const [languages, setLanguages]   = useState<Language[]>([]);

    const [fetchErrors, setFetchErrors] = useState<FetchError[]>([]);

    const {page, size, lang, title}     = useParams();

    const [params, setParams]           = useState<WordsPagFetchParams>(defaultParams);

    const nav = useNavigate();

    const handleError = (error : FetchError) : void => setFetchErrors([ ...fetchErrors, error ]);

    const onPrev = () => setParams({ ...params, page : --params.page });
    const onNext = () => setParams({ ...params, page : ++params.page });

    const onFind = (input : string, langSelect : string) : void => {
        setParams({ ...params, title : input, lang : langSelect});
        const pars = {
            page : params.page.toString(),
            size : params.size.toString(),
            lang : langSelect,
            title : input
        }
        nav({
            pathname : '/words',
            search : `?${createSearchParams(pars)}`
        });

        find(
            e => setFetchErrors([...fetchErrors, e]), 
            r => setWords(r.data), 
            params.page, 
            params.size, 
            input, 
            langSelect
        );
    }

    useEffect(() => {
        onFind(params.title, params.lang);
        fetchLanguages(handleError, r => setLanguages(r.data)); 

        return () => setFetchErrors([]);
    }, []);

    return (
        <div className="content mt-20">

            <Search languages={languages} onFind={onFind}/>
            {fetchErrors == null || fetchErrors.length <= 0
                ? <WordContainer 
                    words={words} 
                    onNext={onNext} 
                    onPrev={onPrev}
                    disablePrev={params.page <= 0}
                />
                : <div>
                    {
                        fetchErrors.map((e, i) => <Fallback 
                            text={buildFallbackMessage(e)} 
                            key={i}
                        />)
                    }
                </div>
            }

        </div>
    );
}