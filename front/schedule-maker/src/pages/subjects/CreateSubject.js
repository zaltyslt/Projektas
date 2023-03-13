import {
  Alert,
  Button,
  Container,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ".././pages.css";

export function CreateSubject() {
  const [name, setName] = useState("");
  const [modules, setModules] = useState([]);
  const [module, setModule] = useState("");
  const [description, setDescription] = useState("");
  const [rooms, setRooms] = useState([]);
  const [nameError, setNameError] = useState(false);
  const [descriptionError, setDescriptionError] = useState(false);
  const [moduleError, setModuleError] = useState(false);
  const [classRoomError, setClassRoomError] = useState(false);
  const [nameNotValid, setNameNotValid] = useState(false);
  const [descriptionNotValid, setDescriptionNotValid] = useState(false);
  const [classRooms, setClassRooms] = useState([]);
  const [error, setError] = useState("");
  const [createMessage, setCreateMessage] = useState("");

  useEffect(() => {
    fetch("api/v1/modules")
      .then((response) => response.json())
      .then(setModules);
  }, []);

  useEffect(() => {
    fetch("api/v1/classrooms/active")
      .then((response) => response.json())
      .then(setRooms);
  }, []);

  const clear = () => {
    setName("");
    setDescription("");
    setModule("");
    setClassRooms([]);
    fetch("api/v1/classrooms/active")
      .then((response) => response.json())
      .then(setRooms);
    setNameError(false);
    setDescriptionError(false);
    setModuleError(false);
    setClassRoomError(false);
    setNameNotValid(false);
    setDescriptionNotValid(false);
  };

  const handleRoomInput = (event) => {
    const {
      target: { value },
    } = event;
    setClassRooms(typeof value === "string" ? value.split(",") : value);
  };

  const validation = () => {
    setCreateMessage("");
    const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
    let notValidName = name.split("").some((char) => badSymbols.includes(char));
    let notValiDescription = description
      .split("")
      .some((char) => badSymbols.includes(char));

    if (
      name === "" &&
      description === "" &&
      module === "" &&
      classRooms.length === 0
    ) {
      setNameError(true);
      setDescriptionError(true);
      setModuleError(true);
      setClassRoomError(true);
    } else if (name === "") {
      setNameError(true);
    } else if (notValidName) {
      setNameError(false);
      setNameNotValid(true);
    } else if (description === "") {
      setDescriptionError(true);
    } else if (notValiDescription) {
      setDescriptionError(false);
      setDescriptionNotValid(true);
    } else if (module === "") {
      setModuleError(true);
    } else if (classRooms.length === 0) {
      setClassRoomError(true);
    } else {
      createSubject();
    }
  };

  const createSubject = () => {
    fetch(`api/v1/subjects`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        module,
        classRooms,
      }),
    }).then((response) => {
      // let statusCode = response.status;
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai sukurta. ");
          setError("");
          clear();
        }
      });
    });
  };

  return (
    <Container>
      <h3 className="create-header">Pridėti naują dalyką</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              error={nameError || nameNotValid}
              helperText={
                nameError
                  ? "Dalyko pavadinimas yra privalomas"
                  : nameNotValid
                  ? "Laukas turi negalimų simbolių. "
                  : ""
              }
              variant="outlined"
              label="Dalyko pavadinimas"
              id="name"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              multiline
              // error={descriptionError || descriptionNotValid}
              helperText={
                descriptionError
                  ? "Dalyko aprašas yra privalomas. "
                  : descriptionNotValid
                  ? "Laukas turi negalimų simbolių. "
                  : ""
              }
              variant="outlined"
              label="Dalyko aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <FormControl fullWidth required error={moduleError}>
              <InputLabel id="module-label">
                {moduleError
                  ? "Privaloma pasirinkti modulį. "
                  : "Modulio pavadinimas"}
              </InputLabel>
              <Select
                label="Modulio pavadinimas"
                labelId="module-label"
                id="module"
                value={module}
                onChange={(e) => {
                  setModule(e.target.value);
                }}
              >
                {modules.map((module) => (
                  <MenuItem key={module.id} value={module}>
                    {module.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={10}>
            <FormControl fullWidth required error={classRoomError}>
              <InputLabel id="room-label">
                {classRoomError
                  ? "Privaloma pasirinkti nors vieną klasę. "
                  : "Klasės"}
              </InputLabel>
              <Select
                multiple
                labelId="room-label"
                id="room"
                value={classRooms}
                onChange={handleRoomInput}
                input={<OutlinedInput label="Klasės" />}
              >
                {rooms.map((room) => (
                  <MenuItem key={room.id} value={room}>
                    {room.classroomName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
              </Button>

              <Link to="/subjects">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
