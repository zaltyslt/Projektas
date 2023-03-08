import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
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

export function CreateProgram(props) {
  const [programName, setProgramName] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState();
  const [success, setSuccess] = useState();
  const [active, setActive] = useState(true);
  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'"
  const invalidNumbers = /^(\d+)?$/
  let navigate = useNavigate();
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [subjects, setSubjects] = useState([]);
  const [subjectError, setSubjectError] = useState(false);
  const [subjectHoursList, setsubjectHoursList] = useState([])
  const [subjectName, setSubjectName] = useState("")
  const [subjectNameError, setSubjectNameError] = useState(false);
  const [errorHours, setErrorHours] = useState(false);
  const clear = () => {
    setProgramName("");
    setDescription("");
    removeAllFields();
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai pridėta!");
      clear();
    } else {
      result
        .text()
        .then((text) => {
          const response = JSON.parse(text);
          setError(response.message);
        })
        .catch((error) => {
          setError("Klasės sukurti nepavyko: ", error);
        });
    }
  };

  useEffect(() => {
    fetch(`api/v1/subjects`)
      .then((response) => response.json())
      .then(setSubjects);
  }, []);

  const checkIfSubjectsIsnotEmpty = () => {
    setSubjectNameError(false)
    var i = 0;
    while (i < subjectHoursList.length) {
      if (subjectHoursList[i].subjectName === '') {
        setSubjectNameError(true)
        return true;
      }
      i++;
    }
    return false;
  }

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

  const createProgram = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
    setSubjectError(false)
    setSubjectNameError(false)
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
      setError("Prašome pridėti dalyką(-us).");
    } else if (checkIfSubjectsIsnotEmpty()) {

    } else if (checkHours()) {

    } else {
      fetch("api/v1/programs/create-program-hours", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          programName,
          description,
          active,
          subjectHoursList,
        }),
      }).then(applyResult);
    }
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  const handleSubjectInput = (event) => {
    const {
      target: { value },
    } = event;
    setSubjects(typeof value === "string" ? value.split(",") : value);
  };

  const handleFormChange = (event, index) => {
    let data = [...subjectHoursList];
    data[index][event.target.name] = event.target.value;
    setsubjectHoursList(data);
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
  const removeAllFields = () => {
    let data = [...subjectHoursList];
    data.splice(0, data.length);
    setsubjectHoursList(data);
  };

  return (
    <div>
      <Container>
        <h3 className="create-header">Pridėti naują programą</h3>
        <form>
          <Grid container id="grid-input">
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
            <Grid item sm={11}>
              <Grid container direction="row" justifyContent="space-between">
                {subjectHoursList.map((form, index) => {
                  return (
                    <Grid container spacing={{ xs: 2, md: 3 }} rowSpacing={{ xs: 5, sm: 5, md: 5 }} columnSpacing={{ xs: 1, sm: 1, md: 1 }} key={index}>
                      <Grid item xs={2}>
                        <FormControl fullWidth required error={subjectNameError}>
                          <InputLabel id="subject-label">
                            {subjectNameError
                              ? "Privaloma pasirinkti dalyką. "
                              : "Dalykas"}</InputLabel>
                          <Select
                            required
                            variant="outlined"
                            labelId="subject-label"
                            label="Dalykas"
                            name="subjectName"
                            label="subjectName"
                            onChange={(event) => handleFormChange(event, index)}
                          >
                            {subjects.map((currentOption) => (
                              <MenuItem
                                key={currentOption.id}
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
                          fullWidth
                          required
                          error={errorHours}
                          helperText={errorHours && "Leidžiami tik skaičių simboliai."}
                          variant="outlined"
                          id="hours"
                          name='hours'
                          placeholder='Valandos'
                          onChange={event => handleFormChange(event, index)}
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
                  )
                })}
              </Grid>
            </Grid>
            <Grid item sm={10}>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={addFields}>
                  Pridėtį dalyką
                </Button>
                <Button variant="contained" onClick={createProgram}>
                  Sukurti
                </Button>
                <Button variant="contained" onClick={() => navigate(-1)}>
                  Grįžti
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
