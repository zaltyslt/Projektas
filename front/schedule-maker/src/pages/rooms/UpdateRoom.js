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

export function UpdateClassroom() {
  const [classroom, setClassroom] = useState({});
  const [error, setError] = useState();
  const [success, setSuccess] = useState();
  const [active, setActive] = useState("");
  const [classroomName, setClassroomName] = useState("");
  const [description, setDescription] = useState("");
  const [building, setBuilding] = useState("AKADEMIJA");
  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";
  const [errorEmptyName, setErrorEmptyName] = useState(false);
  const [errorSymbolsName, setErrorSymbolsName] = useState(false);
  const [errorEmptyDesc, setErrorEmptyDesc] = useState(false);
  const [errorSymbolsDesc, setErrorSymbolsDesc] = useState(false);
  const [errorBuilding, setErrorBuilding] = useState(false);
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorLengthDesc, setErrorLengthDesc] = useState(false);

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleCNameeChange = (event) => {
    setClassroomName(event.target.value);
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  const params = useParams({
    classroomName: "",
    building: "",
    description: "",
    active: classroom.active,
  });

  useEffect(() => {
    fetch(`api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setClassroom(data);
        setClassroomName(data.classroomName);
        setDescription(data.description);
        setBuilding(data.building);
      });
  }, []);

  const updateClassroom = () => {
    setError("");
    setSuccess("");
    setErrorEmptyName(false);
    setErrorSymbolsName(false);
    setErrorEmptyDesc(false);
    setErrorSymbolsDesc(false);
    setErrorBuilding(false);
    if (!classroomName) {
      setErrorEmptyName(true);
    } else if (
      classroomName.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsName(true);
    } else if (!description) {
      setErrorEmptyDesc(true);
    } else if (
      description.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setErrorSymbolsDesc(true);
    } else if (!building) {
      setErrorBuilding(true);
    } else {
      fetch(`api/v1/classrooms/update-classroom/${params.id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description,
          classroomName,
          building,
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
          setSuccess("Klasės duomenys sėkmingai atnaujinti.");
        }
      });
    }
  };

  const disableClassroom = () => {
    fetch(`api/v1/classrooms/disable-classroom/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate(-1));
  };

  const enableClassroom = () => {
    fetch(`api/v1/classrooms/enable-classroom/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate("/rooms"));
  };

  const updateProperty = (property, event) => {
    setClassroom({
      ...classroom,
      [property]: event.target.value,
    });
  };

  return (
    <div>
      <Container>
        <h1 className="edit-header">Redagavimas</h1>
        <h3>{classroom.classroomName}</h3>
        <span id="modified-date">
          Paskutinį kartą redaguota: {classroom.modifiedDate}
        </span>
        <form>
          <Grid container rowSpacing={3}>
            <Grid item sm={8}>
              <FormControl fullWidth required error={errorBuilding}>
                <InputLabel id="building-label">
                  {errorBuilding ? "Prašome pasirinkti pastatą." : "Pastatas"}
                </InputLabel>
                <Select
                  required
                  variant="outlined"
                  labelId="building-label"
                  id="building"
                  label="Pastatas"
                  value={building}
                  onChange={handleChange}
                >
                  <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                  <MenuItem value="TECHIN">TECHIN</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={errorEmptyName || errorSymbolsName || errorLengthName}
                helperText={
                  errorEmptyName
                    ? "Klasės pavadinimas yra privalomas."
                    : errorSymbolsName
                      ? "Klasės pavadinimas turi neleidžiamų simbolių."
                      : errorLengthName
                        ? "Klasės pavadinimas negali būti ilgesnis nei 200 simbolių"
                        : ""
                }
                variant="outlined"
                id="classroomName"
                label="Klasės pavadinimas"
                value={classroomName}
                onChange={(e) => {
                  const input = e.target.value;
                  if (input.length > 200) {
                    setErrorLengthName(true);
                  } else {
                    setErrorLengthName(false);
                  }
                  setClassroomName(input);
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
                    ? "Klasės aprašas yra privalomas."
                    : errorSymbolsDesc
                      ? "Klasės aprašas turi neleidžiamų simbolių."
                      : errorLengthDesc
                        ? "Klasės aprašas negali būti ilgesnis nei 2000 simbolių"
                        : ""
                }
                variant="outlined"
                label="Klasės aprašas"
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
              {" "}
              <legend>{params.classroomName}</legend>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
            </Grid>
            <Grid item sm={4}></Grid>
            <Stack direction="row" spacing={2} marginTop={2}>
              <Button
                id="save-button-edit-room"
                variant="contained"
                onClick={updateClassroom}
              >
                Išsaugoti
              </Button>
              {!classroom.active && (
                <Button
                  variant="contained"
                  data-value="true"
                  value={params.id}
                  onClick={enableClassroom}
                >
                  Aktyvuoti
                </Button>
              )}
              {classroom.active && (
                <Link to="/rooms">
                  <Button
                    id="delete-button-edit-room"
                    variant="contained"
                    data-value="true"
                    value={params.id}
                    onClick={disableClassroom}
                  >
                    Ištrinti
                  </Button>
                </Link>
              )}
              <Link to="/rooms">
                <Button id="back-button-edit-room" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
