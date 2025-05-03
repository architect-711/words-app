import { useState } from 'react';
import { Language } from '../../types/global';
import styles from './Search.module.css';
import { Link } from 'react-router-dom';
import Langs from '../langs/Langs';

interface Params {
    languages : Language[],
    onFind : (input : string, langSelect : string) => void
}

function Search({ languages, onFind } : Params) {
    const [input, setInput] = useState<string>('');
    const [langSelect, setLangSelect] = useState<string>('');

    return (
        <div className={`${styles.container} flex flex-center-between`}>
            <Link to="/words/new" className='btn btn-regular'>Create</Link>

            <div className={`${styles.search_container} flex flex-center-between`}>
                <input 
                    type="text" 
                    placeholder='title...'
                    className={styles.input}
                    value={input}
                    onChange={e => setInput(e.target.value)}
                />

                <Langs langs={languages} onChange={e => setLangSelect(e.target.value)} active='' defaultValue='Select lang'/>

                <button 
                    className='btn btn-regular' 
                    onClick={e => onFind(input, langSelect)}
                >Find</button>
            </div>
        </div>
    );
}

export default Search;