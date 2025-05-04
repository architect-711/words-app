import { useEffect, useState } from "react";
import { FetchError, Language, Word, WordsPagFetchParams } from "../types/global";
import Search from "../components/search/Search";
import Fallback from "../components/words/Fallback";
import { fetchLanguages, fetchWords, find } from "../api/fetchers";
import WordContainer from "../components/words/WordsContainer";
import { buildFallbackMessage } from "../utils/error";
import { createSearchParams, useNavigate, useParams, useSearchParams } from "react-router-dom";
import { getParams } from "../utils/params";

const defaultParams : WordsPagFetchParams = { page : 0 , size : 10, lang: '', title: ''};

export default function WordsPage() {
    const [words, setWords]           = useState<Word[]>([]);
    const [languages, setLanguages]   = useState<Language[]>([]);

    const [fetchErrors, setFetchErrors] = useState<FetchError[]>([]);

    const [searchParams, setSearchParams] = useSearchParams();
    const {page, size, lang, title}     = getParams(searchParams);

    const handleError = (error : FetchError) : void => setFetchErrors([ ...fetchErrors, error ]);

    const fetchWords = () : void => {
        find(
            handleError,
            r => setWords(r.data),
            page,
            size,
            title,
            lang
        );
    }

    useEffect(() => {
        fetchWords();
        fetchLanguages(handleError, r => setLanguages(r.data)); 
    }, [page]);

    return (
        <div className="content mt-20">

            <Search languages={languages}/>
            {fetchErrors == null || fetchErrors.length <= 0
                ? <WordContainer 
                    words={words} 
                    disablePrev={(page ?? 0) <= 0}
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