import { ChangeEvent, HTMLAttributes, InputHTMLAttributes } from "react";

const KittyInput = ({
    type = "text",
    onChange,
    classNames,
    id,
    placeholder,
    other
}: {
    type? : string,
    onChange : (event : ChangeEvent<HTMLInputElement>) => void,
    classNames? : string,
    id? : string,
    placeholder? : string,
    other? : any
}) => {
    return (
        <input 
            type={type}
            className={`${classNames} input input-bottom pd-10 br-10 bg-white`} 
            onChange={onChange}
            id={id}
            placeholder={placeholder}
            {...other}
        />
    );
};

export default KittyInput;