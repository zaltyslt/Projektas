import { Button, Paper, Grid } from "@mui/material";
import { Container, Stack } from "@mui/system";

import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import './ModifyShift.css';

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
            <Container>
                <Grid container rowSpacing={5}>
                    <Grid item lg={12}>
                        <header>
                            <h1>{currentShift.name}</h1>
                            <span id="modified-date">Keista paskutinį kartą: {currentShift.modifiedDate}</span>
                        </header>
                    </Grid>
                    <Grid item lg={12}>
                        <h4>Pamainos Pradžia:</h4>
                        <p>{currentShift.shiftStartingTime}</p>
                    </Grid>
                    <Grid item lg={12}>
                        <h4>Pamainos Pabaiga:</h4>
                        <p>{currentShift.shiftEndingTime}</p>
                    </Grid>
                    <Grid item lg={12}>
                        <h4>Pamaina:</h4>
                        {currentShift.isActive ? 
                            <p>Aktyvi</p> :
                            <p>Neaktyvi</p>
                        }
                    </Grid>
                </Grid>
                <Grid item lg={12}>
                    <Stack direction="row" spacing={2}>
                        <Link to={"/modify-shift/" + currentShift.id}>
                            <Button variant="contained">Redaguoti</Button>
                        </Link>

                        <Link to="/shifts">
                            <Button variant="contained">Grįžti</Button>
                        </Link>
                        <Button variant="contained"> Deaktyvuoti </Button>
                    </Stack>
                </Grid>
            </Container>
          
                        
        </div>
    )
}