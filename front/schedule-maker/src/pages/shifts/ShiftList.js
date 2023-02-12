import { useEffect, useState } from "react";
import { HashRouter, Link, Routes, Route } from "react-router-dom";
import './ShiftList.css';

export function ShiftList() {

    const [activeShifts, setActiveShifts] = useState([]);
    const [inactiveShifts, setInactiveShifts] = useState([]);

    const [isChecked, setIsChecked] = useState(false);

    const [currentActiveShifts, setCurrentActiveShifts] = useState([]);
    const [activeShiftsToShow, setActiveShiftsToShow] = useState([]);

    const [currentPage, setCurrentPage] = useState(1);
    const [amountOfItems, setAmountOfItems] = useState();

    var itemsPerPage = 2;
    
    useEffect(() => {
        fetch( 'http://localhost:8080/api/v1/shift/get-active' )
        .then((response) => response.json())
        .then(data => {
            if (Array.isArray(data)) {
                setActiveShifts(data);
            } else {
                setActiveShifts([data]);
            }
        });
        }, 
    []);

    useEffect(() => {
        fetch( 'http://localhost:8080/api/v1/shift/get-inactive' )
        .then((response) => response.json())
        .then(data => {
            if (Array.isArray(data)) {
                setInactiveShifts(data);
            } else {
                setInactiveShifts([data]);
            }
        });
        }, 
    []);

    useEffect(() => {
        if (typeof activeShifts !== 'undefined') {
            setCurrentActiveShifts(activeShifts);
            setAmountOfItems(activeShifts.length);
        }
        },[activeShifts]);

    const filterActiveShifts = (filterString) => {
        if (filterString.length === 0) {
            setCurrentActiveShifts(activeShifts);
            setAmountOfItems(activeShifts.length);
        }
        if (filterString.length !== 0) {
            var shiftsTemp = activeShifts.filter(shift => shift.name.toLowerCase().includes(filterString.toLowerCase()));
            setCurrentActiveShifts(shiftsTemp);
            setAmountOfItems(shiftsTemp.length);
        }
        setCurrentPage(1);
    }

    const updatePageNumber = ((page) => {
        if (page < 0) {
            if (currentPage + page != 0) {
                setCurrentPage(currentPage - 1);
            }
        }
        else {
            if ((currentPage + page) < amountOfItems / itemsPerPage + page) {
                setCurrentPage(currentPage + 1);
                console.log((currentPage + page) * itemsPerPage);
            }
        }
    });

    useEffect(() => {
        updateActiveShiftsToShow();
        },[currentPage, currentActiveShifts]);
    

    const updateActiveShiftsToShow = (() => {
        setActiveShiftsToShow(currentActiveShifts.slice(
            (currentPage - 1) * itemsPerPage,
            currentPage * itemsPerPage
          )
        );
    })

    return (
        <div>
            <table id="filter-active">
                <tbody>
                    <tr>
                        <td>Filtruoti pagal:</td>
                    </tr>
                    <tr>
                        <td>Vardą: 
                            <input type="text" id="name"
                            onChange={(e) => 
                                filterActiveShifts(e.target.value)
                                }
                            >
                            </input>
                        </td>
                    </tr>
                </tbody>
            </table>

           <table id="shift-list-active"> 
                <tbody>
                    <tr>
                        <td id="shift-header">Vardas</td>
                        <td id="shift-header">Pamainos laikas</td>
                    </tr>
            {activeShiftsToShow.length !== 0 ? 
                activeShiftsToShow.map(shift => (
                    <tr id="shift" key={shift.id}>
                        <td id="shift-name">
                        <Link to={"/view-shift/" + shift.id}
                            id="navigation-button"> 
                            {shift.name}
                        </Link>
                        </td>
                        <td id="shift-start">{shift.shiftTime}</td>
                    </tr>
                )):
                    <tr>
                        <td>Nerasta pamainų</td>
                    </tr>
                }
                <tr>
                    <td id="shift-active-paging">
                        <span onClick={() => updatePageNumber(-1)}>&lt;</span>
                        <span>{currentPage}</span>
                        <span onClick={() => updatePageNumber(1)}>&gt;</span>
                    </td>
                </tr>
               
                </tbody>
            </table>   

            <button> 
                <Link to="/add-shift" id="navigation-button">Pridėti naują </Link>
            </button>

            <br></br>
            
            <input
            type="checkbox"
            checked={isChecked}
            onChange={e => setIsChecked(e.target.checked)}
            /> Rodyti neaktyvias pamainas

            {isChecked && 
                <table id="shift-list-inactive"> 
                    <tbody>
                        <tr>
                            <td id="shift-header">Vardas</td>
                            <td id="shift-header">Pamainos laikas</td>
                            <td id="shift-header">Redaguoti duomenis</td>
                        </tr>
                {inactiveShifts.length !== 0 ? 
                    inactiveShifts.map(shift => (
                        <tr id="shift" key={shift.id}>
                            <td id="shift-name">{shift.name}</td>
                            <td id="shift-start">{shift.shiftTime}</td>
                            <td id="shift-button"> 
                                <button>Aktyvuoti</button>
                            </td>
                        </tr>
                )) :
                    <tfoot>
                    <tr>
                            <td>Nerasta pamainų</td>
                    </tr>
                    </tfoot>
                }
                </tbody>
            </table>  
           
        }
        </div>
    )
}