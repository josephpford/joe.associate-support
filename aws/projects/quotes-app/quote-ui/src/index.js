import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import QuoteGrid from "./QuoteGrid";
import QuoteForm from "./QuoteForm";
import ConfirmDelete from "./ConfirmDelete";

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <div className="container">
        <Routes>
          <Route path="/" element={<QuoteGrid />} />
          <Route path="quote" element={<QuoteForm />}>
            <Route path=":quoteId" element={<QuoteForm />} />
          </Route>
          <Route path="delete/:quoteId" element={<ConfirmDelete />} />
          <Route path="*" element={<h2>Not Found.</h2>} />
        </Routes>
      </div>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

