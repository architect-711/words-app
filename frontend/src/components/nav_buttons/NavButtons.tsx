import { Link, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import styles from './NavButtons.module.css';
import { WordsPagFetchParams } from '../../types/global';
import { getParams } from '../../utils/params';

interface Params {
    disablePrev? : boolean
    disableNext? : boolean
}

type LinkDir = "next" | "prev";

function NavButtons({ disableNext = false, disablePrev = false } : Params) {
    const [searchParams, setSearchParams] = useSearchParams();

    const {page, size, lang, title} = getParams(searchParams);

    const navigate = (type : LinkDir) : void => {
        let p = page ?? 0;

        if (type == 'next') p += 1;
        else p -= 1;

        const params = {
            title : title ?? '',
            lang : lang ?? '',
            page : String(p),
            size : String(size)
        };

        setSearchParams(params);
    }

    return (
        <div className={`${styles.container} pag-buttons mt-10`}>
            <button
                onClick={() => navigate('prev')}
                type="button" 
                className={`${styles.button} btn btn-regular`}
                style={{ display : disablePrev ? 'none' : 'initial' }}
            >Prev</button>
            <button
                onClick={() => navigate('next')}
                type="button" 
                className={`${styles.button} btn btn-regular`}
                style={{ display : disableNext ? 'none' : 'initial' }}
            >Next</button>
        </div>
    );
}

export default NavButtons