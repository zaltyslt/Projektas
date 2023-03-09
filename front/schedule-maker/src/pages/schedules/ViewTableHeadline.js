import React from 'react';
import styled from 'styled-components';

const WEEK_DATA = [
  'Pirmadienis',
  'Antradienis',
  'Treciadienis',
  'Ketvirtadienis',
  'Penktadienis',
  'Sestadienis',
  'Sekamdienis',
];

const LESSONS_COUNT = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14];

export const ViewTableHeadline = () => {
  return (
    <tr>
      <th>Savaite</th>
      {WEEK_DATA.map(day => {
        return (
          <th>
            <DayName>{day}</DayName>
            {LESSONS_COUNT.map(counter => (
              <LessonCell>{counter}</LessonCell>
            ))}
          </th>
        );
      })}
    </tr>
  );
};

const LessonCell = styled.span`
  background-color: lightgrey;
  padding: 4px;
  text-align: center;
  border: 1px solid black;
`;

const DayName = styled.div`
  margin: 10px 0;
`;