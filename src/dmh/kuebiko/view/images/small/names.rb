#! /usr/bin/env ruby

puts Dir.foreach('.')
  .reject { |fn| fn[0] == '.' || File.directory?(fn) }
  .collect { |fn| fn.gsub(/\..*+$/, '').upcase.gsub('-', '_') }
  .join(', ')
