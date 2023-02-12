import { useEffect, useState } from "react"
import { Link } from "react-router-dom";
import './AddShift.css';

export function AddShift() {

    const [name, setName] = useState("")
    const [isValidName, setIsValidName] = useState(true);

    const [shiftStartingTime, setShiftStartingTime] = useState("1");
    const [shiftEndingTime, setShiftEndingTime] = useState("1");
    const [isValidShiftTime, setIsValidShiftTime] = useState(true);

    const [isActive, setIsActive] = useState(true);

    const [shiftCreateMessageError, setShiftCreateMessageError] = useState([]);
    const [successfulPost, setSuccessfulPost] = useState();
    const [isPostUsed, setIsPostUsed] = useState(false);

    const badSymbols = "!@#$%^&*_+={}<>|~`\\\"\'";

    const setNameAndCheck = (name) => {
        setName(name)
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

    const CreateShift = () =>  {
        fetch(
            'http://localhost:8080/api/v1/shift/add-shift', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name,
                    shiftStartingTime,
                    shiftEndingTime,
                    isActive,
                    registered: false
                })
            }
        ) 
        .then(response => response.json())
        .then(data => {
            handleAfterPost(data);
        });
    }

    const handleAfterPost = ((data) => {
            if ((Object.keys(data).length) === 0) {
                setSuccessfulPost(true);
                console.log(isPostUsed)
            }
            else {
                setSuccessfulPost(false);
                setShiftCreateMessageError(data);
            }
            setIsPostUsed(true);
        }
    )

    return (
    <div>
        <h4> Pridėti naują pamainą </h4>
        <table id="shift-add">
            <tbody>
                <tr>
                    <td>Pavadinimas:</td>
                    <td>
                        <form>
                            <input 
                                type="text" id="name" value={name}
                                onChange={(e) => setNameAndCheck(e.target.value)}
                                style={isValidName ? {borderColor: "black"} : {borderColor: "red"}}
                            >
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
                                id="shift-start" value={shiftStartingTime} onChange={(e) => setShiftStartingTime(e.target.value)}>
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
                                id="shift-end" value={shiftEndingTime} onChange={(e) => setShiftEndingTime(e.target.value)}>
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
                    <td>Pamaina Aktyvi:</td>
                    <td>
                        <select
                            id="shift-active" value={isActive} onChange={(e) => setIsActive(e.target.value)}>
                            <option value="true">Taip</option>
                            <option value="false">Ne</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button onClick={() => CreateShift()}>Sukurti Naują Pamainą</button>
                    </td>
                </tr>               
            </tbody>
        </table>

        <footer>
            {isPostUsed ? (
                successfulPost ? (
                    <div id="success-text"> Pamaina Sėkmingai Pridėta!</div>
                    ) : 
                    (
                    <div id="error-text">
                        <div>Nepavyko Sukurti Pamainos</div>
                    {Object.keys(shiftCreateMessageError).map(key => (
                    <div key={key} id="error-text"> {shiftCreateMessageError[key]} </div>
                        ))}
                    </div>
                    )
                ) : 
                (
                <div></div>
                )}
        </footer>
        <button> 
                <Link to="/shifts" id="navigation-button">Grįžti atgal</Link>
        </button>
    </div>
    )
}