export default function Fallback({ 
    text, 
    style 
} : { 
    text : string, 
    style? : any 
}) {
    return <p
                className="mt-10 br-10 pd-10"
                style={style == null ? {backgroundColor : 'red', color : '#fff'} : style}
    >
        {text}
    </p>
}