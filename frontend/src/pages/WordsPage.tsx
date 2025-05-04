import { useEffect, useState } from "react";
import { FetchError, Language, Word } from "../types/global";
import Search from "../components/search/Search";
import Fallback from "../components/words/Fallback";
import {buildFuns, deleteWordById, fetchLanguages, find} from "../api/fetchers";
import WordContainer from "../components/words/WordsContainer";
import { buildFallbackMessage } from "../utils/error";
import { useSearchParams } from "react-router-dom";
import { getParams } from "../utils/params";

export default function WordsPage() {
    const [words, setWords]           = useState<Word[]>([]);
    const [languages, setLanguages]   = useState<Language[]>([]);

    const [fetchErrors, setFetchErrors] = useState<FetchError[]>([]);

    const [searchParams, setSearchParams] = useSearchParams();
    const {page, size, lang, title}     = getParams(searchParams);

    const handleError = (error : FetchError) : void => setFetchErrors([ ...fetchErrors, error ]);

    const fetchWords = () : void => {
        find(buildFuns(handleError, r => setWords(r.data)), page, size, title, lang);
    }

    const onDelete = (id : number) : void => {
        if (id < 0) {
            setFetchErrors([...fetchErrors, {
                name : 'Impossible',
                message : 'You did something impossible, the id of the word you are trying to delete is negative! -_-',
                description : 'The delete button for the word was called, but id for some impossible reason is negative'
            }]);
        }

        deleteWordById(id, buildFuns(
            e => setFetchErrors([...fetchErrors, e]),
            () => setWords(words.filter(w => w.id !== id))
        ));
    }

    useEffect(() => {
        fetchWords();
        fetchLanguages({ eFn : handleError, okFn:  r => setLanguages(r.data)} );
    }, [page]);

    return (
        <div className="content mt-20">

            <Search languages={languages}/>
            {fetchErrors == null || fetchErrors.length <= 0
                ? <WordContainer 
                    words={words} 
                    disablePrev={(page ?? 0) <= 0}
                    onDelete={onDelete}
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