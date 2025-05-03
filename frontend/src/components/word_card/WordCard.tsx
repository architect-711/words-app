import { useEffect, useState } from 'react';
import { FetchError, Language, Word, WordForm } from '../../types/global';
import styles from './WordCard.module.css';
import { fetchLanguages, updateWord } from '../../api/fetchers';
import Fallback from '../words/Fallback';
import WordCardContent from './WordCardContent';
import { EMPTY_WORD } from '../../utils/word';
import { buildFallbackMessage } from '../../utils/error';
import WordsService from '../../api/WordsService';

const WordCard = ({ 
    word = EMPTY_WORD,
    onSave
} : { 
    word? : Word,
    onSave : (f : WordForm) => void
}) => {
    const [langs, setLangs] = useState<Language[]>([]);
    const [error, setError] = useState<FetchError | null>(null);

    useEffect(() => {
        fetchLanguages(setError, r => setLangs(r.data));
    }, []);

    return (
        <div className='mt-20 pd-10' id={styles.container}>

            {
                error != null
                    ? <Fallback text={buildFallbackMessage(error)}/>
                    : <WordCardContent word={word} langs={langs} onSave={onSave}/>
            }
        </div>
    );
};

export default WordCard;