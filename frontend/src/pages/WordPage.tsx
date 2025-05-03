import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { FetchError, Word, WordForm } from "../types/global";
import Fallback from "../components/words/Fallback";
import WordCard from "../components/word_card/WordCard";
import { fetchWordById, updateWord } from "../api/fetchers";
import { buildFallbackMessage } from "../utils/error";

export default function WordPage() {
    const [word, setWord]             = useState<Word | null>(null);
    const [fetchError, setFetchError] = useState<FetchError | null>(null);

    const { id } = useParams();

    const handleFetchError = (error : FetchError) => setFetchError(error);
    
    const onSave = (f : WordForm) : void => {
        updateWord(f, setFetchError, _ => window.location.reload());
    }

    useEffect(() => {
        fetchWordById(Number(id), handleFetchError, r => setWord(r.data));
    }, []);

    return (
        <div className="content">
            {
                fetchError != null 
                ? <Fallback text={buildFallbackMessage(fetchError)} />
                : word == null 
                    ? <Fallback text="Internal error. The word for some reason is null."/>
                    : <WordCard word={word} onSave={onSave}/> 
            }
        </div>
    );
}