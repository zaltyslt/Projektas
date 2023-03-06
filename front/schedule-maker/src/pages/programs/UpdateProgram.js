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
    colors,
    Alert,
    AlertTitle,
    OutlinedInput,
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
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [subjects, setSubjects] = useState([]);
  const [subjectHoursList, setsubjectHoursList] = useState([]);

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

  const updateProgram = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
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
              setError("Klasės sukurti nepavyko: ", error);
            });
        } else {
          setSuccess("Sėkmingai atnaujinote!");
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
          Paskutinį kartą redaguota: {program.modifiedDate}
        </span>
        <form>
          <Grid container id="grid-input" >
            <Grid item sm={12} id="grid-selector">
              <TextField
                fullWidth
                required
                error={errorEmptyName || errorSymbolsName}
                helperText={
                  errorEmptyName
                    ? "Programos pavadinimas yra privalomas."
                    : errorSymbolsName
                    ? "Programos pavadinimas turi neleidžiamų simbolių."
                    : ""
                }
                variant="outlined"
                id="programName"
                label="Programos pavadinimas"
                value={programName}
                onChange={(e) => setProgramName(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item sm={12} id="grid-selector">
              <TextField
                fullWidth
                multiline
                required
                error={errorEmptyDesc || errorSymbolsDesc}
                helperText={
                  errorEmptyDesc
                    ? "Programos aprašas yra privalomas."
                    : errorSymbolsDesc
                    ? "Programos aprašas turi neleidžiamų simbolių."
                    : ""
                }
                variant="outlined"
                label="Programos aprašas"
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item sm={12}>
              {" "}
              <legend>{params.programName}</legend>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
            </Grid>
            <Grid item sm={11}>
              <Grid container direction="row" justifyContent="space-between">
                {subjectHoursList.map((form, index) => {
                  return (
                    <Grid
                      container
                      marginBottom={2}
                      spacing={{ xs: 2, md: 3 }}
                      rowSpacing={{ xs: 5, sm: 5, md: 5 }}
                      columnSpacing={{ xs: 1, sm: 1, md: 1 }}
                      key={index}
                    >
                      <Grid item xs={5}>
                        <FormControl fullWidth>
                          {/* <InputLabel id="subject-label">Dalykas</InputLabel> */}
                          <Select
                            value={form.subjectName}
                            onChange={(event) => handleFormChange(event, index)}
                            // required
                            // variant="outlined"
                            // placeholder='Dalykas'
                            // labelId="subject-label"
                            // label="Dalykas"
                            name="subjectName"
                            // label='subjectName'
                          >
                            {subjects.map((currentOption) => (
                              <MenuItem
                                key={currentOption.name}
                                value={currentOption.name}
                              >
                                {currentOption.name}
                              </MenuItem>
                            ))}
                          </Select>
                        </FormControl>
                      </Grid>
                      <Grid item xs={4}>
                        <TextField
                          name="hours"
                          placeholder="Valandos"
                          onChange={(event) => handleFormChange(event, index)}
                          value={form.hours}
                        />
                      </Grid>
                      <Grid
                        item
                        xs={3}
                        container
                        justifyContent="end"
                        justifyItems={"center"}
                        alignContent={"center"}
                        paddingRight={0.5}
                      >
                        <Button
                          variant="contained"
                          onClick={() => removeFields(index)}
                        >
                          Ištrinti
                        </Button>
                      </Grid>
                    </Grid>
                  );
                })}
              </Grid>
            </Grid>
            <Grid item sm={12}>
              <Stack direction="row" spacing={3}>
                <Button variant="contained" onClick={addFields}>
                  Pridėtį dalyką
                </Button>
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
