import WordItem from "./WordItem";
import { Word } from "../../types/global";
import styles from './Words.module.css';
import NavButtons from "../nav_buttons/NavButtons";
import Fallback from "./Fallback";

interface Params {
    words : Word[],
    disablePrev : boolean
}

export default function WordContainer({ words, disablePrev = false } : Params) {
    const isWordsEmpty = () : boolean => words == null || words.length == 0;

    return (
        <div className={`${styles.words_container} mt-20`}>

                {isWordsEmpty()
                    ? <Fallback text="No words found!"/>
                    : <div className="words">
                        {words.map((w, i) => <WordItem key={i} word={w}/>)}
                    </div>
                }

            <NavButtons 
                disablePrev={disablePrev}
            />

        </div>
    );
}