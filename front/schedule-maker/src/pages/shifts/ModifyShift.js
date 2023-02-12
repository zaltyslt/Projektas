import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

export function ModifyShift() {

    const params = useParams();

    const [currentShift, setCurrentShift] = useState([]);

    const [isValidName, setIsValidName] = useState(true);
    const [isValidShiftTime, setIsValidShiftTime] = useState(true);

    const [name, setShiftName] = useState("");
    const [shiftStartingTime, setShiftStartingTime] = useState("1");
    const [shiftEndingTime, setShiftEndingTime] = useState("1");

    useEffect(() => {
        fetch( 'http://localhost:8080/api/v1/shift/view-shift/' + params.id)
        .then((response) => response.json())
        .then(data => {
            setCurrentShift(data);
        });
    }, []);

    useEffect(() => {
        if (currentShift.startIntEnum && currentShift.endIntEnum && currentShift.name) {
            setShiftStartingTime(currentShift.startIntEnum.toString());
            setShiftEndingTime(currentShift.endIntEnum.toString());
            setShiftName(currentShift.name);
        } 
    }, [currentShift])

    const ModifyShift = () => {
        fetch(
            'http://localhost:8080/api/v1/shift/modify-shift/' + params.id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name,
                    shiftStartingTime,
                    shiftEndingTime,
                    registered: false
                })
            }
        )
    }

    const badSymbols = "!@#$%^&*_+={}<>|~`\\\"\'";

    const setNameAndCheck = (name) => {
        setShiftName(name);
        const isValid = name.split('').some(char => badSymbols.includes(char));
        if (isValid) {
            setIsValidName(false);
        }
        else {
            setIsValidName(true);
        }
    }

    useEffect(() => {
        if (shiftStartingTime > shiftEndingTime) {
            setIsValidShiftTime(false);
        }
        else {
            setIsValidShiftTime(true);
        }
    }, [shiftStartingTime, shiftEndingTime]);

    return (
       <div>
        <h4>Redaguoti Pamainą:</h4>
        <table>
            <tbody>
                <tr>
                    <td>Keista Paskutinį Kartą:</td>
                    <td>{currentShift.modifiedDate}</td>
                </tr>
                <tr>
                    <td>Pavadinimas:</td>
                    <td>
                        <form>
                            <input 
                                type="text" id="name" defaultValue={currentShift.name}
                                onChange={(e) => setNameAndCheck(e.target.value)}
                                style={isValidName ? {borderColor: "black"} : {borderColor: "red"}}>
                            </input>
                        </form>
                    </td>  
                </tr>
                {isValidName ? 
                    <tr><td></td></tr> :
                    <tr><td id="error-text"> Negalimas Simbolis Panaudotas Pavadinime! </td></tr>
                }
                <tr>
                    <td>Pamainos Pradžia:</td>
                    <td>
                        <form>
                            <select
                                id="shift-start" value={shiftStartingTime} 
                                onChange={(e) => setShiftStartingTime(e.target.value)}>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                        </form>
                    </td>
                    {isValidShiftTime ?
                        <td></td>:
                        <td id="error-text"> Pamainos Laikas Negalimas!</td>
                    }
                </tr>
                <tr>
                    <td>Pamainos Pabaiga:</td>
                    <td>
                        <form>
                            <select
                                id="shift-end" value={shiftEndingTime}
                                onChange={(e) => setShiftEndingTime(e.target.value)}>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                        </form>
                    </td>
                    {isValidShiftTime ?
                        <td></td>:
                        <td id="error-text"> Pamainos Laikas Negalimas!</td>
                    }
                </tr>
            </tbody>
        </table>
        <button> 
                <Link to="/shifts" id="navigation-button">Grįžti atgal</Link>
        </button>
        <button onClick={() => ModifyShift()}> 
            Pakeisti Duomenis
        </button>
       </div>
    )
}