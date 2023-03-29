import { Container } from "@mui/system";
import {
  Alert,
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Grid,
  Stack,
  TextField,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams, useHref } from "react-router-dom";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { dateToUtc } from "../../helpers/helpers";
import "./Schedule.css";
import { BAD_SYMBOLS } from "../../helpers/constants";

export function EditHoliday() {
  const [name, setName] = useState("");
  const [holiday, setHoliday] = useState("");
  const [scheduleId, setScheduleId] = useState("");
  const [nameEmpty, setNameEmpty] = useState(false);
  const [validName, setValidName] = useState(false);
  const [createMessage, setCreateMessage] = useState("");
  const [error, setError] = useState("");
  const [openPrompt, setOpenPrompt] = useState(false);
  const params = useParams();
  const calendarUrl = useHref(`/schedules/${scheduleId}`);

  useEffect(() => {
    fetch(`api/v1/schedules/holiday/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setHoliday(data);
        setName(data.name);
        setScheduleId(data.schedule.id);
      });
  }, []);

  const validateName = (value) => {
    setName(value);
    value.length === 0 ? setNameEmpty(true) : setNameEmpty(false);
    const isValid = value.split("").some((char) => BAD_SYMBOLS.includes(char));
    isValid ? setValidName(true) : setValidName(false);
  };

  const updateHoliday = () => {
    fetch(`api/v1/schedules/holidays/update-holiday/${params.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: name,
    }).then((response) => {
      let success = response.ok;
      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai atnaujinta. ");
          setError("");
        }
      });
    });
  };

  const validation = () => {
    let isValid = true;
    if (name === "" || name === "undefined") {
      setNameEmpty(true);
      isValid = false;
    }
    if (validName) {
      isValid = false;
    }
    
    if (isValid) {
      setCreateMessage("");
      updateHoliday();
    }
  };

  const handleDelete = () => {
    fetch(`api/v1/schedules/holidays/delete-holiday/${params.id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = calendarUrl));
  };

  const handleClose = () => {
    setOpenPrompt(false);
  };

  const handlePrompt = () => {
    setOpenPrompt(true);
  };

  return (
    <div>
      <Container>
        <Dialog
          open={openPrompt}
          keepMounted
          onClose={handleClose}
          aria-describedby="alert-dialog-slide-description"
        >
          <DialogTitle>
            {"Ar tikrai norite ištrinti pasirinktą atostogų dieną?"}
          </DialogTitle>
          <DialogActions>
            <Button onClick={handleClose}>Atšaukti</Button>
            <Button onClick={handleDelete}>Ištrinti</Button>
          </DialogActions>
        </Dialog>
        <h3>Atostogų redagavimas</h3>
        <h5>{holiday.date}</h5>
        <Grid container rowSpacing={2} spacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Pavadinimas"
              id="name"
              name="name"
              value={name}
              onChange={(e) => validateName(e.target.value)}
              error={nameEmpty || validName}
              helperText={
                validName
                  ? "Laukas turi negalimų simbolių."
                  : nameEmpty
                    ? "Pavadinimas yra privalomas."
                    : ""
              }
            ></TextField>
          </Grid>
          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-edit-holiday"
                variant="contained"
                onClick={validation}
              >
                Išsaugoti
              </Button>
              <Link to={"/schedules/" + scheduleId}>
                <Button id="back-button-edit-holiday" variant="contained">
                  Grįžti
                </Button>
              </Link>
              <Button
                id="delete-button-edit-holiday"
                variant="contained"
                onClick={handlePrompt}
              >
                Ištrinti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
