import { FetchError } from "../types/global";

export const errorHandler = (
    e : unknown,
    eFn : (error : FetchError) => void
) => {
    if (e instanceof Error)
        eFn(buildFE(e.message, e.name, 'Unknown'))
    else
        eFn(buildFE('Unknown', 'Unkown', 'Unknown'));
}

export const buildFE = (
    message : string,
    name : string,
    description : string
) : FetchError => {
    return {
        name : name,
        description : description,
        message : message
    };
}

export const buildFallbackMessage = (e : FetchError) : string => {
    return `${bm(e.name, 'Name: ', '.')} ${bm(e.message, 'Message', '.')} ${bm(e.description, 'Description')}`
};

const bm = (
    val : string | undefined, 
    prefix? : string,
    postfix? : string
) : string => {
    let res : string = '';

    if (val == null || val == undefined)
        return res = '';
    
    res = val

    if (prefix != null)
        res = prefix + ' ' + res;
    if (postfix != null)
        res += postfix + ' '

    return res;
}