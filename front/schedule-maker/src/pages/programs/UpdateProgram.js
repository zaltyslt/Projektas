import * as React from "react";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
    Button,
    Container,
    FormControl,
    Grid,
    Select,
    Stack,
    TextField,
    InputLabel,
    MenuItem,
    Alert,
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";


export function UpdateProgram() {
    const [program, setProgram] = useState({});
    const [error, setError] = useState();
    const [success, setSuccess] = useState();
    const [active, setActive] = useState("");
    const [programName, setProgramName] = useState("");
    const [description, setDescription] = useState("");
    const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";

    const handleCNameeChange = (event) => {
        setProgramName(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const params = useParams({
        setProgramName: "",
        description: "",
        active: program.active,
    });

    useEffect(() => {
        fetch(`/api/v1/programs/program/${params.id}`)
            .then((response) => response.json())
            .then((data) => {
                setProgram(data);
                setProgramName(data.programName);
                setDescription(data.description);
            });
    }, []);

    const updateProgram = () => {
        setError("");
        setSuccess("");
        if (!programName) {
            setError("Prašome užpildyti programos pavadinimą.");
        } else if (
            programName.split("").some((char) => invalidSymbols.includes(char))
        ) {
            setError("Programos pavadinimas turi neleidžiamų simbolių.");
        } else if (!description) {
            setError("Prašome užpildyti programos aprašą.");
        } else if (
            description.split("").some((char) => invalidSymbols.includes(char))
        ) {
            setError("Programos aprašas turi neleidžiamų simbolių.");
        } else {
            fetch(`/api/v1/programs/update-program/${params.id}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    programName,
                    description,
                }),
            }).then((result) => {
                if (!result.ok) {
                    setError("Redaguoti nepavyko!");
                } else {
                    setSuccess("Sėkmingai atnaujinote!");
                }
            });
        }
    };

    const disableProgram = () => {
        fetch(`/api/v1/programs/disable-program/${params.id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(() => navigate(-1));
    };

    const enableProgram = () => {
        fetch(`/api/v1/programs/enable-program/${params.id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
        }).then(() => navigate("/programs"));
    };

    const updateProperty = (property, event) => {
        setProgram({
            ...program,
            [property]: event.target.value,
        });
    };


    return (
        <div>
            <Container>
                <h1>Redagavimas</h1>
                <h3>{program.programName}</h3>
                <h5>Paskutinį kartą redaguota: {program.modifiedDate}</h5>
                <form>
                    <Grid container rowSpacing={2}>
                        <Grid item sm={10}>
                            <TextField
                                fullWidth
                                required
                                id="programName"
                                label="Programos pavadinimas"
                                value={programName}
                                onChange={(e) => setProgramName(e.target.value)}
                            ></TextField>
                        </Grid>
                        <Grid item sm={10}>
                            <TextField
                                fullWidth
                                multiline
                                required
                                label="Programos aprašas"
                                id="description"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            ></TextField>
                        </Grid>
                        <Grid item sm={10}>
                            {" "}
                            <legend>{params.programName}</legend>
                            {error && (
                                <Alert severity="warning">
                                    {error}
                                </Alert>
                            )}
                            {success && (
                                <Alert severity="success">
                                    {success}
                                </Alert>
                            )}
                        </Grid>
                        <Grid item sm={10}>
                            <Stack direction="row" spacing={2}>
                                <Button variant="contained" onClick={updateProgram}>
                                    Išsaugoti
                                </Button>
                                {!program.active && (
                                    <Button
                                        variant="contained"
                                        data-value="true"
                                        value={params.id}
                                        onClick={enableProgram}
                                    >
                                        Aktyvuoti
                                    </Button>
                                )}
                                {program.active && (
                                    <Link to="/programs">
                                        <Button
                                            variant="contained"
                                            data-value="true"
                                            value={params.id}
                                            onClick={disableProgram}
                                        >
                                            Ištrinti
                                        </Button>
                                    </Link>
                                )}
                                <Link to="/programs">
                                    <Button variant="contained">Grįžti</Button>
                                </Link>
                            </Stack>
                        </Grid>
                    </Grid>
                </form>
            </Container>
        </div>
    );
}
