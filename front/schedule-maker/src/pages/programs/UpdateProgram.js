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
    const invalidNumbers = /^(\d+)?$/
    const [errorEmptyName, setErrorEmptyName] = useState(false);
    const [errorSymbolsName, setErrorSymbolsName] = useState(false);
    const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
    const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
    const [subjects, setSubjects] = useState([])
    const [subjectHoursList, setsubjectHoursList] = useState([])
    const [subjectNameError, setSubjectNameError] = useState(false);
    const [errorHours, setErrorHours] = useState(false);
    const [errorLengthName, setErrorLengthName] = useState(false);
    const [errorLengthDesc, setErrorLengthDesc] = useState(false);

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
    fetch("api/v1/subjects")
      .then((response) => response.json())
      .then(setSubjects);
    fetch(`api/v1/programs/program/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setProgram(data);
        setProgramName(data.programName);
        setDescription(data.description);
        setsubjectHoursList(data.subjectHoursList);
      });
  }, []);

  const checkIfSubjectsIsnotEmpty = () => {
    setSubjectNameError(false);
    var i = 0;
    while (i < subjectHoursList.length) {
      if (subjectHoursList[i].subjectName === "") {
        setSubjectNameError(true);
        return true;
      }
      i++;
    }
    return false;
  };

  const checkHours = () => {
    setErrorHours(false);
    let hasErrors = false;
    subjectHoursList.forEach(({ hours }) => {
      if (!invalidNumbers.test(hours)) {
        setErrorHours(true);
        hasErrors = true;
      }
    });
    return hasErrors;
  };

  const updateProgram = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
    setSubjectNameError(false);
    setErrorHours(false);
    if (!programName) {
      setErrorEmptyName(true);
    } else if (
      programName.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsName(true);
    } else if (!description) {
      setErrorEmptyDesc(true);
    } else if (
      description.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsDesc(true);
    } else if (subjectHoursList.length === 0) {
      setError("Pra??ome prid??ti dalyk??(-us).");
    } else if (checkIfSubjectsIsnotEmpty()) {
    } else if (checkHours()) {
    } else {
      fetch(`api/v1/programs/update-hours-program/${params.id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          programName,
          description,
          subjectHoursList,
        }),
      }).then((result) => {
        if (!result.ok) {
          result
            .text()
            .then((text) => {
              const response = JSON.parse(text);
              setError(response.message);
            })
            .catch((error) => {
              setError("Programos sukurti nepavyko: ", error);
            });
        } else {
          setSuccess("S??kmingai atnaujinote!");
        }
      });
    }
  };

  const disableProgram = () => {
    fetch(`api/v1/programs/disable-program/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate(-1));
  };

  const enableProgram = () => {
    fetch(`api/v1/programs/enable-program/${params.id}`, {
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

  const addFields = () => {
    let object = {
      subjectName: "",
      hours: "",
    };
    setsubjectHoursList([...subjectHoursList, object]);
  };

  const removeFields = (index) => {
    let data = [...subjectHoursList];
    data.splice(index, 1);
    setsubjectHoursList(data);
  };

  const handleFormChange = (event, index) => {
    let data = [...subjectHoursList];
    if (event.target.name === "subjectName") {
      data[index]["subjectName"] = event.target.value;
    } else {
      data[index][event.target.name] = event.target.value;
    }
    setsubjectHoursList(data);
  };

  const handleSubjectInput = (event) => {
    const {
      target: { value },
    } = event;
    setSubjects(typeof value === "string" ? value.split(",") : value);
  };

  return (
    <div>
        <Container>
            <h1 className="edit-header">Redagavimas</h1>
            <h3>{program.programName}</h3>
            <span id="modified-date">
                Paskutin?? kart?? redaguota: {program.modifiedDate}
            </span>
            <form>
                <Grid container rowSpacing={3}>
                    <Grid item sm={10}>
                        <TextField
                            fullWidth
                            required
                            error={errorEmptyName || errorSymbolsName || errorLengthName}
                            helperText={errorEmptyName ? "Programos pavadinimas yra privalomas."
                              : errorSymbolsName
                                ? "Programos pavadinimas turi neleid??iam?? simboli??."
                                : errorLengthName
                                ? "Programos pavadinimas negali b??ti ilgesnis nei 200 simboli??"
                                : ""}
                            variant="outlined"
                            id="programName"
                            label="Programos pavadinimas"
                            value={programName}
                            onChange={(e) => {
                                const input = e.target.value;
                                if (input.length > 200) {
                                  setErrorLengthName(true);
                                } else {
                                  setErrorLengthName(false);
                                }
                                setProgramName(input);
                              }}
                            // onChange={(e) => setProgramName(e.target.value)}
                        ></TextField>
                    </Grid>
                    <Grid item sm={10}>
                        <TextField
                            fullWidth
                            multiline
                            required
                            error={errorEmptyDesc || errorSymbolsDesc || errorLengthDesc}
                            helperText={
                              errorEmptyDesc
                                ? "Programos apra??as yra privalomas."
                                : errorSymbolsDesc
                                  ? "Programos apra??as turi neleid??iam?? simboli??."
                                  : errorLengthDesc
                                  ? "Programos apra??as negali b??ti ilgesnis nei 2000 simboli??"
                                  : ""}
                            variant="outlined"
                            label="Programos apra??as"
                            id="description"
                            value={description}
                            onChange={(e) => {
                                const input = e.target.value;
                                if (input.length > 2000) {
                                  setErrorLengthDesc(true);
                                } else {
                                  setErrorLengthDesc(false);
                                }
                                setDescription(input);
                              }}
                            // onChange={(e) => setDescription(e.target.value)}
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
                    <Grid item sm={12} >
                        <Grid container direction="row" justifyContent="space-between">
                            {subjectHoursList.map((form, index) => {
                                return (
                                    <Grid container spacing={{ xs: 2, md: 3 }} rowSpacing={{ xs: 5, sm: 5, md: 5 }} columnSpacing={{ xs: 1, sm: 1, md: 1 }} key={index}>
                                        <Grid item xs={2}>
                                            <FormControl fullWidth required error={subjectNameError}>
                                                <InputLabel id="subject-label">
                                                    {subjectNameError
                                                        ? "Privaloma pasirinkti dalyk??. "
                                                        : "Dalykas"}
                                                </InputLabel>
                                                <Select
                                                    required
                                                    variant="outlined"
                                                    labelId="subject-label"
                                                    label="Dalykas"
                                                    name='subjectName'
                                                    label='subjectName'
                                                    value={form.subjectName}
                                                    onChange={event => handleFormChange(event, index)}
                                                >
                                                    {subjects.map(currentOption => (
                                                        <MenuItem key={currentOption.id} value={currentOption.name}>
                                                            {currentOption.name}
                                                        </MenuItem>
                                                    ))}
                                                </Select>
                                            </FormControl>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <TextField
                                                fullWidth
                                                required
                                                error={errorHours}
                                                helperText={errorHours && "Leid??iami tik skai??i?? simboliai."}
                                                variant="outlined"
                                                id="hours"
                                                name='hours'
                                                placeholder='Valandos'
                                                onChange={event => handleFormChange(event, index)}
                                                value={form.hours}
                                            />
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Button onClick={() => removeFields(index)}>I??trinti</Button>
                                        </Grid>
                                    </Grid>
                                )
                            })}
                        </Grid>
                    </Grid>
                    <Grid item sm={10}>
                        <Stack direction="row" spacing={2}>
                            <Button variant="contained" onClick={addFields}>Prid??t?? dalyk??</Button>
                            <Button variant="contained" onClick={updateProgram}>
                                I??saugoti
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
                                        I??trinti
                                    </Button>
                                </Link>
                            )}
                            <Link to="/programs">
                                <Button variant="contained">Gr????ti</Button>
                            </Link>
                        </Stack>
                    </Grid>
                </Grid>
            </form>
        </Container>
    </div >
);
}