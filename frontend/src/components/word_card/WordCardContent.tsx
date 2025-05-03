import { InputHTMLAttributes, useState } from 'react';
import { Language, Word, WordForm } from '../../types/global';
import Langs from '../langs/Langs';
import KittyInput from '../shared/input/KittyInput';
import styles from './WordCard.module.css';
import Usecase from './Usecase';

const WordCardContent = ({
    word, 
    langs,
    onSave 
} : { 
    word : Word, 
    langs : Language[],
    onSave : (f : WordForm) => void
}) => { 
    const [form, setForm] = useState<WordForm>(word);

    const reset = () : void => setForm(word);
    const onRemove = (u : string) => {
        setForm({ 
            ...form, 
            useCases : form.useCases.filter(o => o !== u)
        });
    }
    const addUseCase = (i : number, v : string) : string[] => {
        form.useCases[i] = v;

        return form.useCases;
    }

    const inc = () : string[] => {
        if (form.useCases == null) form.useCases = []
        form.useCases.push(""); 

        return form.useCases;
    }

    return (
        <>
            <div className="top">
                <p id={styles.edit_inform}>Click to edit</p>
                <p>{word.localDateTime}</p>
            </div>

            <div className="main_shit">
                <KittyInput 
                    onChange={e => setForm({ ...form, title : e.target.value})}
                    id={styles.title}
                    placeholder='title'
                    other={{ value: form.title }}
                />

                <p id={styles.translation}>from <Langs 
                    langs={langs} 
                    active={word.language} 
                    defaultValue='Choose language'
                    onChange={e => setForm({ ...form, language : e.target.value})}
                /> - <KittyInput 
                    onChange={e => setForm({ ...form, translation : e.target.value })}
                    placeholder='translation'
                    other={{ value : form.translation }}
                    id={styles.translation}
                    classNames='mt-10'
                /></p>
                
                <textarea
                    onChange={e => setForm({ ...form, description : e.target.value })}
                    id={styles.description}
                    placeholder='description'
                    value={form.description}
                    className='mt-10 fs-22 br-10 input pd-10'
                />
            </div>

            <div id={styles.use_cases_container}>
                <h3 className='mt-10'>Usecases:</h3>

                <ul className={styles.use_case}>
                    {
                        form.useCases != null && form.useCases.map((u, i) => <Usecase 
                            useCase={u} 
                            key={i} 
                            onChange={e => setForm({ ...form, useCases : addUseCase(i, e.target.value)})}
                            onRemove={onRemove}
                        />)
                    }
                </ul>

                <button 
                    onClick={() => setForm({ ...form, useCases : inc() })}
                    className='btn btn-regular mt-10'
                >Add usecase</button>
            </div>


            <div className={`${styles.buttons} mt-10`}>
                <button 
                    className='btn btn-primary' onClick={() => onSave(form)}>Save</button>
                <button className='btn btn-regular' onClick={reset} type='reset'>Reset</button>
            </div>
        </>
    );
};

export default WordCardContent;