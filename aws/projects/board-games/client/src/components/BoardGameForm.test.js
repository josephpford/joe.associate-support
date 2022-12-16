import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router';
import { Route } from 'react-router-dom';
import * as api from '../services/boardGameApi';
import * as categoryApi from '../services/categoryApi';
import BoardGameForm from './BoardGameForm';

const categories = [
  { id: 1, name: "Miniatures" },
  { id: 2, name: "Euro" }
];

it('should load category list', async () => {
  categoryApi.findAll = jest.fn().mockImplementation(() => Promise.resolve(categories));

  await act(async() => {
    render(
      <MemoryRouter>
        <BoardGameForm />
      </MemoryRouter>
    );
  });

  expect(categoryApi.findAll).toBeCalledTimes(1);

  const categoryInputs = document.querySelectorAll('input[name="category"]');
  expect(categoryInputs.length).toBe(2);
});

it('should add a new game', async () => {
  api.add = jest.fn().mockImplementation(() => Promise.resolve({}));
  categoryApi.findAll = jest.fn().mockImplementation(() => Promise.resolve(categories));

  await act(async() => {
    render(
      <MemoryRouter>
        <BoardGameForm />
      </MemoryRouter>
    );
  });

  act(() => {
    userEvent.type(document.getElementById("name"), "Lunar Rush");
    userEvent.type(document.getElementById("imageUrl"), "https://fake.come/image.jpg");
    userEvent.type(document.getElementById("minimumPlayers"), "{backspace}1");
    userEvent.type(document.getElementById("maximumPlayers"), "{backspace}4");
    userEvent.type(document.getElementById("rating"), "{backspace}8.8");
    userEvent.click(document.getElementById("inPrint"));
    userEvent.click(document.getElementById("category-1"));
  
    userEvent.click(document.querySelector('button[type="submit"]'));
  });

  expect(api.add).toBeCalledTimes(1);

  expect(api.add.mock.calls[0][0]).toStrictEqual({ 
    id: 0, 
    name: "Lunar Rush", 
    imageUrl: "https://fake.come/image.jpg", 
    minimumPlayers: 1, 
    maximumPlayers: 4,
    rating: 8.8,
    inPrint: false,
    categories: [
      { id: 1, name: "Miniatures" }
    ]
  });
});
