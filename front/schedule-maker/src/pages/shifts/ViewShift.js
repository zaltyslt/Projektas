import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

export function ViewShift() {
    
    const params = useParams();

    const [currentShift, setCurrentShift] = useState([]);

    useEffect(() => {
        fetch( 'http://localhost:8080/api/v1/shift/view-shift/' + params.id)
        .then((response) => response.json())
        .then(data => {
            setCurrentShift(data);
        });
    }, []);

    return (
        <div>
            <h4>Pasirinkta Pamaina:</h4>
            <table>
                <tbody>
                    <tr>
                        <td>Keista Paskutinį Kartą:</td>
                        <td>{currentShift.modifiedDate}</td>
                    </tr>
                    <tr>
                        <td>Pavadinimas:</td>
                        <td>{currentShift.name}</td>
                    </tr>
                    <tr>
                        <td>Pamainos Pradžia:</td>
                        <td>{currentShift.shiftStartingTime}</td>
                    </tr>
                    <tr>
                        <td>Pamainos Pabaiga:</td>
                        <td>{currentShift.shiftEndingTime}</td>
                    </tr>
                    <tr>
                        <td>Pamaina:</td>
                        {currentShift.isActive ? 
                            <td>Aktyvi</td> :
                            <td>Neaktyvi</td>
                        }
                    </tr>
                </tbody>
            </table>

            <button> 
                <Link to="/shifts" id="navigation-button">Grįžti atgal</Link>
            </button>
            <button> 
            <Link to={"/modify-shift/" + currentShift.id}id="navigation-button"> Redaguoti</Link>
            </button>
            <button> 
                <Link to="/shifts" id="navigation-button">Deaktyvuoti</Link>
            </button>
                        
        </div>
    )
}