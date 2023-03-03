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
  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";
  let navigate = useNavigate();
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [subjects, setSubjects] = useState([])
  const [subjectError, setSubjectError] = useState(false);
  const [subjectHoursList, setsubjectHoursList] = useState([
    // { hours: '', subjectName: '' }
  ])

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
      setError("Nepavyko sukurti!");
    }
  };

  useEffect(() => {
    fetch("api/v1/subjects")
      .then((response) => response.json())
      .then(setSubjects);
  }, []);

  const createProgram = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
    setSubjectError(false)
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
    } else if (!subjects) {
      setSubjectError(true)
    } else {
      fetch("/api/v1/programs/create-program-hours", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          programName,
          description,
          active,
          subjectHoursList
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

    // if (event.target.name === '') {
      console.log(event.target.value)
      console.log(event.target.name)
      // data[index]['subjectName'] = event.target.value;
    // } else {
      data[index][event.target.name] = event.target.value;
    // }
    setsubjectHoursList(data);
  }

  const addFields = () => {
    let object = {
      subjectName: '',
      // subject: '',
      hours: ''
    }
    setsubjectHoursList([...subjectHoursList, object])
  }

  const removeFields = (index) => {
    let data = [...subjectHoursList];
    data.splice(index, 1)
    setsubjectHoursList(data)
  }
  const removeAllFields = () => {
    let data = [...subjectHoursList];
    data.splice(0, data.length)
    setsubjectHoursList(data)
  }

  return (
    <div>
      <Container>
        <h3 className="create-header">Pridėti naują programą</h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={errorEmptyName || errorSymbolsName}
                helperText={errorEmptyName ? "Programos pavadinimas yra privalomas."
                  : errorSymbolsName
                    ? "Programos pavadinimas turi neleidžiamų simbolių."
                    : ""}
                variant="outlined"
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
                error={errorEmptyDesc || errorSymbolsDesc}
                helperText={
                  errorEmptyDesc
                    ? "Programos aprašas yra privalomas."
                    : errorSymbolsDesc
                      ? "Programos aprašas turi neleidžiamų simbolių."
                      : ""}
                variant="outlined"
                label="Programos aprašas"
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></TextField>
            </Grid>
            <Grid
              container
              direction="column"
              justifyContent="space-around"
              alignItems="flex-start"
            >
              {/* <Grid item sm={6}>
                <FormControl fullWidth required error={subjectError}>
                  <InputLabel id="subject-label">{subjectError ? "Privaloma pasirinkti nors vieną dalyką. " : "Dalykai"}</InputLabel>
                  <Select
                    multiple
                    labelId="subject-label"
                    id="subject"
                    value={subjects}
                    onChange={handleSubjectInput}
                    input={<OutlinedInput label="Dalykai" />}
                  >
                    {subjects.map((subject) => (
                      <MenuItem key={subject.id} value={subject}>
                        {subject.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid> */}
              {subjectHoursList.map((form, index) => {
                return (
                  <div key={index}>
                    <br />
                    <Select
                    name='subjectName'
                    label='sdf'
                    // placeholder="ert"
                    // value={subjects}
                      onChange={event => handleFormChange(event, index)}
                    >
                      {subjects.map(currentOption => (
                        <MenuItem key={currentOption.id} value={currentOption.name}>
                          {currentOption.name}
                        </MenuItem>

                      ))}
                    </Select>
                    <TextField
                      name='hours'
                      placeholder='Hours'
                      onChange={event => handleFormChange(event, index)}
                      value={form.hours}
                    />
                    <Button onClick={() => removeFields(index)}>Remove</Button>
                  </div>
                )
              })}
            </Grid>
            <Grid item sm={10}>
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
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={addFields}>Pridėtį dalyką</Button>
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
    </div >
  )
}