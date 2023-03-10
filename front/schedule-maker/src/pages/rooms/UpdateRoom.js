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
    setErrorBuilding(false)
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
          result.text().then(text => {
            const response = JSON.parse(text);
            setError(response.message)
          }).catch(error => {
            setError("Klas??s sukurti nepavyko: ", error);
          });
        } else {
          setSuccess("S??kmingai atnaujinote!");
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
          Paskutin?? kart?? redaguota: {classroom.modifiedDate}
        </span>
        <form>
          <Grid container rowSpacing={3}>
            <Grid item sm={10}>
            <FormControl fullWidth required error={errorBuilding}>
              <InputLabel id="building-label">
                {errorBuilding
                  ? "Pra??ome pasirinkti pastat??."
                  : "Pastatas"}</InputLabel>
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
            <Grid item sm={10}>
              <TextField
                fullWidth
                required
              error={errorEmptyName || errorSymbolsName || errorLengthName }
              helperText={
                errorEmptyName
                  ? "Klas??s pavadinimas yra privalomas."
                  : errorSymbolsName
                    ? "Klas??s pavadinimas turi neleid??iam?? simboli??." 
                    : errorLengthName
                    ? "Klas??s pavadinimas negali b??ti ilgesnis nei 200 simboli??"
                    : ""
              }
                variant="outlined"
                id="classroomName"
                label="Klas??s pavadinimas"
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
                // onChange={(e) => setClassroomName(e.target.value)}

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
                    ? "Klas??s apra??as yra privalomas."
                    : errorSymbolsDesc
                      ? "Klas??s apra??as turi neleid??iam?? simboli??."
                      : errorLengthDesc
                      ? "Klas??s apra??as negali b??ti ilgesnis nei 2000 simboli??"
                      : ""
                }
                variant="outlined"
                label="Klas??s apra??as"
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
              <legend>{params.classroomName}</legend>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
            </Grid>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={updateClassroom}>
                I??saugoti
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
                    variant="contained"
                    data-value="true"
                    value={params.id}
                    onClick={disableClassroom}
                  >
                    I??trinti
                  </Button>
                </Link>
              )}
              <Link to="/rooms">
                <Button variant="contained">Gr????ti</Button>
              </Link>
            </Stack>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
