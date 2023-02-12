import { HashRouter, Link, Routes, Route } from "react-router-dom";

export function ViewShift(props) {

    const func = (() => {
        console.log(props.location.state.shiftID)
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