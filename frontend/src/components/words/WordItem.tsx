import { Word } from "../../types/global";
import styles from './Words.module.css';
import { Link } from "react-router-dom";

interface Props {
    word : Word,
    onDelete : (id : number) => void
}

export default function WordItem({ word, onDelete } : Props) {

    const preDelete = () : void => {
        if (window.confirm(`Delete word ${word.title} with id ${word.id}?`))
            onDelete(word.id ?? -1);
    }

    return (
        <div className={`${styles.word_container} flex flex-centere-between`}>

            <Link to={"/words/" + word.id} className={styles.link}>
                <table id={styles.table}>
                    <tr>
                        <th>{word.title}</th>
                        <td>{word.translation}</td>
                        <td>{word.language}</td>
                    </tr>
                </table>
            </Link>

            <button className="btn btn-regular" onClick={preDelete}>Delete</button>
        </div>
    );
}