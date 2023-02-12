import {  Link, useParams } from "react-router-dom";

export function ViewShift() {
    
    const params = useParams();

    const func = (() => {
        console.log(params)
        console.log("hehe")
    })

    return (
        <div>
            Sup
            <button onClick={func}> Button

            </button>
        </div>
    )
}