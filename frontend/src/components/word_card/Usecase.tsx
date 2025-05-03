import React, { ChangeEvent } from 'react';
import KittyInput from '../shared/input/KittyInput';

const Usecase = ({
    useCase,
    onChange,
    onRemove
}: {
    useCase: string,
    onChange : (e : ChangeEvent<HTMLTextAreaElement>) => void,
    onRemove : (u : string) => void
}) => {
    return (
        <li className='flex'>
            <textarea
                value={useCase}
                onChange={onChange}
                className='input br-10 pd-10'
            />
            <button 
                className='btn btn-regular'
                onClick={() => onRemove(useCase)}
            >x</button>
        </li>
    );
};

export default Usecase;