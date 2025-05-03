import React, { useState } from 'react';
import WordCard from '../components/word_card/WordCard';
import { FetchError, WordForm } from '../types/global';
import { saveWord } from '../api/fetchers';
import Fallback from '../components/words/Fallback';
import { buildFallbackMessage } from '../utils/error';

const NewWordPage = () => {
    const [error, setError] = useState<FetchError | null>(null);

    const onSave = (f : WordForm) : void => {
        saveWord(f, setError, r => window.location.href = `${window.location.origin}/words/${r.data.id}`);
    }

    return (
        <div className='content'>

            { error == null
                ? <WordCard onSave={onSave}/> 
                : <Fallback text={buildFallbackMessage(error)}/>
            }

        </div>
    );
};

export default NewWordPage;