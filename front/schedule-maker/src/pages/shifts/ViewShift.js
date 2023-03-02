import { Button, Grid } from "@mui/material";
import { Container, Stack } from "@mui/system";

import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import './ViewShift.css';

export function ViewShift() {
    
    const params = useParams();

    const [currentShift, setCurrentShift] = useState([]);

    useEffect(() => {
        fetch( 'api/v1/shift/view-shift/' + params.id)
        .then((response) => response.json())
        .then(data => {
            setCurrentShift(data);
        });
    }, []);

    return (
        <div>
            <Container>
                <Grid container rowSpacing={4}>
                    <Grid item sm={10}>
                        <header>
                            <h1>{currentShift.name}</h1>
                            <span id="modified-date">Paskutinį kartą redaguota: {currentShift.modifiedDate}</span>
                        </header>
                    </Grid>

                    <Grid item sm={12}>
                        <h4>Pamainos pradžia:</h4>
                        <p>{currentShift.shiftStartingTime}</p>
                    </Grid>

                    <Grid item sm={12}>
                        <h4>Pamainos pabaiga:</h4>
                        <p>{currentShift.shiftEndingTime}</p>
                    </Grid>

                <Grid item sm={12}>
                    <Stack direction="row" spacing={2}>
                        <Link to={"/modify-shift/" + currentShift.id}>
                            <Button variant="contained">Redaguoti</Button>
                        </Link>
                        <Link to="/shifts">
                            <Button variant="contained">Grįžti</Button>
                        </Link>
                    </Stack>
                </Grid>
                </Grid>
            </Container>           
        </div>
    );
}