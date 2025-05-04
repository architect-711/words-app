import { useState } from 'react';
import { Language } from '../../types/global';
import styles from './Search.module.css';
import { Link } from 'react-router-dom';
import Langs from '../langs/Langs';

interface Params {
    languages : Language[],
}

function Search({ languages } : Params) {
    return (
        <div className={`${styles.container} flex flex-center-between`}>
            <Link to="/words/new" className='btn btn-regular'>Create</Link>

            <form className={`${styles.search_container} flex flex-center-between`}>
                <input 
                    type="text" 
                    name='title'
                    placeholder='title...'
                    className={styles.input}
                />

                <Langs 
                    name='lang'
                    langs={languages} 
                    active='' 
                    defaultValue='Select lang'
                />

                <button 
                    className='btn btn-regular' 
                    type='submit'
                >Find</button>
            </form>
        </div>
    );
}

export default Search;