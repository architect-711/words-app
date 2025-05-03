import { NavLink } from 'react-router-dom';
import styles from './Head.module.css';

export default function Head() {
    return (
        <header className={styles.header}>

            <div className="content flex flex-center-between">
                <div className="title">
                    <NavLink to="/" id={styles.title}>WordsApp</NavLink>
                </div>

                <div className={styles.links}>
                    <NavLink to="/words" className={styles.link}>Words</NavLink>
                    <NavLink to="/" className={styles.link}>Quiz</NavLink>
                </div>
            </div>

        </header>
    ); 
}
