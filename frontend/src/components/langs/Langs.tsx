import { ChangeEvent, ChangeEventHandler } from 'react';
import { Language } from '../../types/global';

const Langs = ({
    langs,
    active,
    defaultValue,
    onChange 
} : {
    langs : Language[],
    active : string,
    defaultValue? : string,
    onChange : (e : ChangeEvent<HTMLSelectElement>) => void 
}) => {
    return (
        <select 
            name="" 
            id=""
            onChange={onChange}
            className='input pd-10 br-10 bg-white'
        >
            { defaultValue != null && <option value="">{defaultValue}</option> }

            {
                langs.map((l, i) => <option
                    value={l.title}
                    key={i}
                    selected={l.title == active}
                    >
                        {l.title}
                </option>)
            }
        </select>
    );
};

export default Langs;