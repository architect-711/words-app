import { title } from "process";
import { Word } from "../../types/global";
import styles from './Words.module.css';
import { Link } from "react-router-dom";

export default function WordItem({ word } : { word : Word }) {
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

            <button className="btn btn-regular">Delete</button>
        </div>
    );
}