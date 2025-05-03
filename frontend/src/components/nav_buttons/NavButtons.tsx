import styles from './NavButtons.module.css';

interface Params {
    onPrev : () => void,
    onNext : () => void,
    disablePrev? : boolean
    disableNext? : boolean
}

function NavButtons({ onPrev, onNext, disableNext = false, disablePrev = false } : Params) {
    return (
        <div className={`${styles.container} pag-buttons mt-10`}>
            <button 
                type="button" 
                onClick={onPrev}
                className={`${styles.button} btn btn-regular`}
                disabled={disablePrev}
            >Prev</button>
            <button 
                type="button" 
                onClick={onNext}
                className={`${styles.button} btn btn-regular`}
                disabled={disableNext}
            >Next</button>
        </div>
    );
}

export default NavButtons