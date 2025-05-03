import { AxiosResponse } from "axios";
import { FetchError, NoPromiseServiceResponse } from "../types/global";

export default function handleBackendError<T>(
    response : NoPromiseServiceResponse<T>,
    setError : (error : FetchError) => void,
    okFn     : (data : AxiosResponse<T>) => void
) {
    if ('localDateTime' in response)
        setError({
            message : response.message,
            localDateTime : response.localDateTime,
            description : response.description
        }); 
    else
        okFn(response);
};