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
  const invalidNumbers = /^(\d+)?$/;
  let navigate = useNavigate();
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [subjects, setSubjects] = useState([]);
  const [subjectError, setSubjectError] = useState(false);
  const [subjectHoursList, setsubjectHoursList] = useState([]);
  const [subjectName, setSubjectName] = useState("");
  const [subjectNameError, setSubjectNameError] = useState(false);
  const [errorHours, setErrorHours] = useState(false);
  const [errorHoursNumber, setErrorHoursNumber] = useState(false);
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorLengthDesc, setErrorLengthDesc] = useState(false);

  const clear = () => {
    setProgramName("");
    setDescription("");
    removeAllFields();
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Programa sėkmingai sukurta.");
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
    setErrorHoursNumber(false);
    let hasErrors = false;
    subjectHoursList.forEach(({ hours }) => {
      if (!invalidNumbers.test(hours)) {
        setErrorHours(true);
        hasErrors = true;
      } else if (Number(hours) > 1000) {
        setErrorHoursNumber(true);
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
    setSubjectError(false);
    setSubjectNameError(false);
    setErrorHours(false);
    if (
      programName === "" &&
      description === "" &&
      subjectHoursList.length === 0
    ) {
      setErrorEmptyName(true);
      setErrorEmptyDesc(true);
      setErrorHours(true);
    } else if (programName === "") {
      setErrorEmptyName(true);
    } else if (description === "") {
      setErrorEmptyDesc(true);
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
    console.log(event.target.value);
    let data = [...subjectHoursList];
    if (event.target.name == "subjectName") {
      data[index][event.target.name] = event.target.value.name;
      data[index]["deleted"] = event.target.value.deleted;
      data[index]["subject"] = event.target.value.id;
    } else {
      data[index][event.target.name] = event.target.value;
    }
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
          <Grid container rowSpacing={2}>
            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={errorEmptyName || errorSymbolsName || errorLengthName}
                helperText={
                  errorEmptyName
                    ? "Programos pavadinimas yra privalomas."
                    : errorSymbolsName
                      ? "Programos pavadinimas turi neleidžiamų simbolių."
                      : errorLengthName
                        ? "Programos pavadinimas negali būti ilgesnis nei 200 simbolių"
                        : ""
                }
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
              ></TextField>
            </Grid>
            <Grid item sm={8}>
              <TextField
                fullWidth
                multiline
                required
                error={errorEmptyDesc || errorSymbolsDesc || errorLengthDesc}
                helperText={
                  errorEmptyDesc
                    ? "Programos aprašas yra privalomas."
                    : errorSymbolsDesc
                      ? "Programos aprašas turi neleidžiamų simbolių."
                      : errorLengthDesc
                        ? "Programos aprašas negali būti ilgesnis nei 2000 simbolių"
                        : ""
                }
                variant="outlined"
                label="Programos aprašas"
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
              ></TextField>
            </Grid>

            
            <Grid item sm={8}>
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
                      <Grid item xs={6}>
                        <FormControl
                          fullWidth
                          required
                          error={subjectNameError}
                        >
                          <InputLabel id="subject-label">
                            {subjectNameError
                              ? "Privaloma pasirinkti dalyką. "
                              : "Dalykas"}
                          </InputLabel>
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
                                value={currentOption}
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
                          error={errorHours || errorHoursNumber}
                          helperText={
                            errorHours
                              ? "Leidžiami tik skaičių simboliai."
                              : errorHoursNumber
                                ? "Dalykas negali viršyti 1000 valandų."
                                : ""
                          }
                          variant="outlined"
                          id="hours"
                          name="hours"
                          placeholder="Valandos"
                          onChange={(event) => handleFormChange(event, index)}
                          value={form.hours}
                        />
                      </Grid>
                      <Grid
                        item
                        xs={2}
                        container
                        justifyContent="end"
                        justifyItems={"center"}
                        alignContent={"center"}
                        paddingRight={0.5}
                      >
                        <Button
                          id="delete-subject-button-create-program"
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
            <Grid item sm={8}>
              <Stack direction="row" spacing={2} marginBottom={2}>
                <Button
                  id="add-subject-button-create-program"
                  variant="contained"
                  onClick={addFields}
                >
                  Pridėtį dalyką
                </Button>
                <Button
                  id="save-button-create-program"
                  variant="contained"
                  onClick={createProgram}
                >
                  Išsaugoti
                </Button>
                <Button
                  id="back-button-create-program"
                  variant="contained"
                  onClick={() => navigate(-1)}
                >
                  Grįžti
                </Button>
              </Stack>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
