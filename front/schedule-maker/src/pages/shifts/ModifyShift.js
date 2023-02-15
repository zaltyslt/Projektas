import {
    Button,
    Grid,
    MenuItem,
    Select,
    FormHelperText,
    TextField,
  } from "@mui/material";
import { Container, Stack } from "@mui/system";

import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import './ModifyShift.css';

export function ModifyShift() {

    const params = useParams();

    const [currentShift, setCurrentShift] = useState([]);

    const [isValidName, setIsValidName] = useState(true);
    const [isNameEmpty, setIsNameEmpty] = useState(false);

    const [isValidShiftTime, setIsValidShiftTime] = useState(true);

    const [name, setName] = useState("");
    const [shiftStartingTime, setShiftStartingTime] = useState("1");
    const [shiftEndingTime, setShiftEndingTime] = useState("1");

    const [successfulPost, setSuccessfulPost] = useState();
    const [isPostUsed, setIsPostUsed] = useState(false);
    const [shiftCreateMessageError, setShiftCreateMessageError] = useState([]);
    const [isActive, setIsActive] = useState("");


    useEffect(() => {
        fetch( 'http://localhost:8080/api/v1/shift/view-shift/' + params.id)
        .then((response) => response.json())
        .then(data => {
            setCurrentShift(data);
        });
    }, []);

    const deactivateShift = ((shiftID) => {
        fetch(
            'http://localhost:8080/api/v1/shift/deactivate-shift/' + shiftID, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        )
    })

    var startIntEnum;
    var endIntEnum;

    const modifyShift = (() => {
        if (isValidName && !isNameEmpty) {
            startIntEnum = shiftStartingTime;
            endIntEnum = shiftEndingTime;
            modifyShiftPutRequest();
        }
    })

    const modifyShiftPutRequest = () => {
        fetch(
            'http://localhost:8080/api/v1/shift/modify-shift/' + params.id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name,
                    startIntEnum,
                    endIntEnum,
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

    useEffect(() => {
        if (currentShift.startIntEnum && currentShift.endIntEnum && currentShift.name) {
            setShiftStartingTime(currentShift.startIntEnum.toString());
            setShiftEndingTime(currentShift.endIntEnum.toString());
            setName(currentShift.name);
            setIsActive(currentShift.isActive);
        } 
    }, [currentShift])


    const handleAfterPost = ((data) => {
        if ((Object.keys(data).length) === 0) {
            setSuccessfulPost(true);
        }
        else {
            setSuccessfulPost(false);
            setShiftCreateMessageError(data);
        }
        setIsPostUsed(true);
    })

    const badSymbols = "!@#$%^&*_+={}<>|~`\\\"\'";

    const setNameAndCheck = (name) => {
        setName(name);
        if (name.length === 0) {
            setIsNameEmpty(true);
        }
        else {
            setIsNameEmpty(false);
        }
        const isValid = name.split('').some(char => badSymbols.includes(char));
        if (isValid) {
            setIsValidName(false);
        }
        else {
            setIsValidName(true);
        }
    }

    useEffect(() => {
        if (parseInt(shiftStartingTime) > parseInt(shiftEndingTime)) {
            setIsValidShiftTime(false);
        }
        else {
            setIsValidShiftTime(true);
        }
    }, [shiftStartingTime, shiftEndingTime]);

    const lessonTimes = [
        { value: '1', label: '1 pamoka' },
        { value: '2', label: '2 pamoka' },
        { value: '3', label: '3 pamoka' },
        { value: '4', label: '4 pamoka' },
        { value: '5', label: '5 pamoka' },
        { value: '6', label: '6 pamoka' },
        { value: '7', label: '7 pamoka' },
        { value: '8', label: '8 pamoka' },
        { value: '9', label: '9 pamoka' },
        { value: '10', label: '10 pamoka' },
        { value: '11', label: '11 pamoka' },
        { value: '12', label: '12 pamoka' },
        { value: '13', label: '13 pamoka' },
        { value: '14', label: '14 pamoka' },
      ];

    return (
       <div>
         <Container>
            <h1>Redagavimas</h1> 
            <h3>{currentShift.name}</h3>
            <Grid container id="grid-input">
                <Grid item lg={10}>
                    <TextField
                    fullWidth
                    required
                    error={!isValidName || isNameEmpty}
                    helperText={
                        !isValidName ? "Pavadinimas turi neleidžiamų simbolių." : 
                        isNameEmpty ? "Pavadinimas negali būti tuščias" : null
                    }
                    variant="outlined"
                    label="Pamainos pavadinimas"
                    id="name"
                    value={name}
                    onChange={(e) => setNameAndCheck(e.target.value)}
                    ></TextField>
                </Grid>
            </Grid>

            <Grid container rowSpacing={2}>
                <Grid item lg={2} id="grid-selector">
                    <h5>Pamainos pradžia:</h5>
                    <Select
                    fullWidth
                    multiline
                    error={!isValidShiftTime}
                    variant="outlined"
                    label="Pamainos pradžia" 
                    id="description"
                    value={shiftStartingTime}
                    onChange={(e) => setShiftStartingTime(e.target.value)}>
                    {lessonTimes.map((lessonTime) => (
                        <MenuItem key={lessonTime.value} value={lessonTime.value}>
                        {lessonTime.label}
                        </MenuItem>
                    ))}
                    </Select>
                    {!isValidShiftTime && (
                    <FormHelperText error>
                        Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                    </FormHelperText>
                    )}
                </Grid>
                
                <Grid item lg={2} id="grid-selector">
                    <h5>Pamainos pabaiga:</h5>
                    <Select
                    fullWidth
                    multiline
                    error={!isValidShiftTime}
                    variant="outlined"
                    label="Pamainos pabaiga"
                    id="description"
                    value={shiftEndingTime}
                    onChange={(e) => setShiftEndingTime(e.target.value)}>
                    {lessonTimes.map((lessonTime) => (
                        <MenuItem key={lessonTime.value} value={lessonTime.value}>
                        {lessonTime.label}
                        </MenuItem>
                    ))}
                    </Select>
                    {!isValidShiftTime && (
                    <FormHelperText error>
                        Pirma pamoka negali prasidėti vėliau negu paskutinė pamoka.
                    </FormHelperText>
                    )}
                </Grid> 
            </Grid>
            <Grid item lg={2}>
                <Stack direction="row" spacing={2}>
                    <Button variant="contained" onClick={modifyShift}>
                        Išsaugoti
                    </Button>
                    <Link to="/shifts">
                        <Button variant="contained" onClick={() => deactivateShift(currentShift.id)}> 
                            Ištrinti
                        </Button>
                    </Link> 
                    <Link to="/shifts">
                        <Button variant="contained">Grįžti</Button>
                    </Link>     
                </Stack>
            </Grid>
            <Grid>
                {isPostUsed ? (
                    successfulPost ? (
                        <div id="success-text"> Pamaina sėkmingai pakeista.</div>
                        ) : 
                        (
                        <div id="error-text">
                            <div>Nepavyko pakeisti pamainos.</div>
                        {Object.keys(shiftCreateMessageError).map(key => (
                        <div key={key} id="error-text"> {shiftCreateMessageError[key]} </div>
                            ))}
                        </div>
                        )
                    ) : 
                    (
                    <div></div>
                    )}
            </Grid>
        </Container>
       </div>
    )
}