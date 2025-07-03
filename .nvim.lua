-- Local nvim config to evaluate Clojure files with Conjure on every save
vim.api.nvim_create_autocmd("BufWritePost", {
  pattern = "*.clj",
  callback = function()
    vim.cmd("ConjureEvalFile")
  end,
  desc = "Evaluate Clojure file with Conjure on save"
})
